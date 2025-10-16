package com.osmar.themoviedbapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.osmar.themoviedbapp.data.datastore.LanguageManager
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.repositories.GenreRepository
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepository
import com.osmar.themoviedbapp.data.repositories.MovieListRepository
import com.osmar.themoviedbapp.datastore.LanguagePreferences.Languages
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.ui.home.screen.View
import com.osmar.themoviedbapp.utils.LocaleHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val genreRepository: GenreRepository,
    private val languageManager: LanguageManager,
    private val movieDaoRepository: MovieDaoRepository,
): ViewModel() {

    private var _activeView = MutableStateFlow(View.NOW_PLAYING)
    val activeView = _activeView.asStateFlow()

    private val _movieList = MutableStateFlow<UiState<Flow<PagingData<MovieModel>>>>(UiState.Start)
    val movieList = _movieList.asStateFlow()

    private val _genreNameList = MutableStateFlow<UiState<List<String>>>(UiState.Start)
    val genreNameList = _genreNameList.asStateFlow()

    private var _isFavorite = MutableStateFlow(false)
    val isFavorite = _isFavorite.asStateFlow()


    private var _currentLanguage = MutableStateFlow(LocaleHelper.getDeviceLanguage())

    init {
        getLanguageFlag()
    }

    fun fetchMovies(view : View){
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = UiState.Loading
            _activeView.update { view }
            movieListRepository.getMovieListPagination(view.key, transformLanguage(_currentLanguage.value)).let { result ->
                _movieList.update { UiState.Success(result) }
            }
        }
    }

    fun fetchGenresName(genresIDs : List<Long>){
        viewModelScope.launch(Dispatchers.IO) {
            _genreNameList.value = UiState.Loading
            genreRepository.getGenreList(transformLanguage(_currentLanguage.value)).let { resultResponse ->
                when(resultResponse){
                    is ResultResponse.Error -> {
                        Log.d("ResultResponse", "Error")
                        _genreNameList.value = UiState.Error(resultResponse.message)
                    }
                    is ResultResponse.Success ->{
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



    private fun setLocaleLanguage(){
        viewModelScope.launch {
            languageManager.setLanguage(
                _currentLanguage.value
            )
            fetchMovies(View.NOW_PLAYING)
        }
    }

    private fun getLanguageFlag(){
        viewModelScope.launch {
            languageManager.isLanguageChange.collect{ languageFlag ->
                if (languageFlag){
                    setCurrentLanguage()
                }else{
                    setLocaleLanguage()
                }
                Log.d(HomeViewModel::class.simpleName,"LanguageFlag = $languageFlag")
            }
        }
    }

    private fun setCurrentLanguage(){
        viewModelScope.launch {
            languageManager.languageSelected.collect{
                _currentLanguage.value = it
                fetchMovies(View.NOW_PLAYING)

                Log.d(HomeViewModel::class.simpleName,it.toString())
            }
        }
    }

    private fun transformLanguage(language : Languages): String {
        return when(language){
            Languages.SPANISH -> "es"
            Languages.ENGLISH -> "en"
            else -> "en"
        }
    }

}