package com.osmar.themoviedbapp.ui.menu.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.themoviedbapp.data.database.entity.toModel
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepository
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val movieDaoRepository: MovieDaoRepository
): ViewModel() {

    private var _movieList = MutableStateFlow<UiState<List<MovieModel>>>(UiState.Start)
    val movieList = _movieList.asStateFlow()

    init {
        fetchLocalMovies()
    }

    private fun fetchLocalMovies() {
        viewModelScope.launch {
            _movieList.value = UiState.Loading
            movieDaoRepository.fetchLocalMoviesList().collect{ localMovies ->
                _movieList.update {
                    UiState.Success(localMovies.map {
                        it.toModel()
                    })
                }
            }
        }
    }

    fun removeToFavorite(movie : MovieModel){
        viewModelScope.launch {
            movieDaoRepository.deleteLocalMovie(movie)
                .collect{
                    fetchLocalMovies()
                }
        }
    }

}