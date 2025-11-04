package com.osmar.themoviedbapp.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.osmar.themoviedbapp.data.datastore.LanguageManager
import com.osmar.themoviedbapp.data.repositories.MovieListRepository
import com.osmar.themoviedbapp.ui.MovieModel
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.utils.LocaleHelper
import com.osmar.themoviedbapp.utils.Utils
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
    private val languageManager: LanguageManager,
): ViewModel() {

    private var _activeView = MutableStateFlow(View.NOW_PLAYING)
    val activeView = _activeView.asStateFlow()

    private val _movieList = MutableStateFlow<UiState<Flow<PagingData<MovieModel>>>>(UiState.Start)
    val movieList = _movieList.asStateFlow()

    private var _currentLanguage = MutableStateFlow(LocaleHelper.getDeviceLanguage())

    init {
        getLanguageFlag()
    }

    fun fetchMovies(view : View){
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = UiState.Loading
            _activeView.update { view }
            movieListRepository.getMovieListPagination(view.key, Utils.transformLanguage(_currentLanguage.value)).let { result ->
                _movieList.update { UiState.Success(result) }
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

}