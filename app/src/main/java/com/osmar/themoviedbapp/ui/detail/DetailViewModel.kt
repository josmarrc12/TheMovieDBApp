package com.osmar.themoviedbapp.ui.detail

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.repositories.GenreRepository
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepository
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.HomeViewModel
import com.osmar.themoviedbapp.ui.MovieModel
import com.osmar.themoviedbapp.utils.LocaleHelper
import com.osmar.themoviedbapp.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val genreRepository: GenreRepository,
    private val movieDaoRepository: MovieDaoRepository,
) : ViewModel() {

    private val _genreNameList = MutableStateFlow<UiState<List<String>>>(UiState.Start)
    val genreNameList = _genreNameList.asStateFlow()

    private var _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()

    private var _currentLanguage = MutableStateFlow(LocaleHelper.getDeviceLanguage())

    fun fetchGenresName(genresIDs: List<Long>) {
        viewModelScope.launch(Dispatchers.IO) {
            _genreNameList.value = UiState.Loading
            genreRepository.getGenreList(Utils.transformLanguage(_currentLanguage.value))
                .let { resultResponse ->
                    when (resultResponse) {
                        is ResultResponse.Error -> {
                            Log.d("ResultResponse", "Error")
                            _genreNameList.value = UiState.Error(resultResponse.message)
                        }

                        is ResultResponse.Success -> {
                            Log.d("ResultResponse", "Success")
                            val genreNames = resultResponse.data.genres.filter {
                                it.id in genresIDs
                            }.map { it.name }
                            Log.d("genresNames", genreNames.toString())
                            _genreNameList.update {
                                UiState.Success(genreNames)
                            }
                        }
                    }
                }
        }
    }

    fun isFavorite(idMovie : Long){
        viewModelScope.launch(Dispatchers.IO) {
            movieDaoRepository.fetchLocalMovieDetails(idMovie)
                .collect{ movie ->
                    _isFavorite.value = movie != null
                    Log.d(HomeViewModel::class.simpleName,(movie != null).toString())
                }
        }
    }

    fun addToFavorite(movie: MovieModel){
        viewModelScope.launch(Dispatchers.IO) {
            movieDaoRepository.insertLocalMovie(movie)
                .collect{
                    isFavorite(movie.id)
                }
        }
    }

    fun removeToFavorite(movie : MovieModel){
        viewModelScope.launch {
            movieDaoRepository.deleteLocalMovie(movie)
                .collect{
                    isFavorite(movie.id)
                }
        }
    }


}