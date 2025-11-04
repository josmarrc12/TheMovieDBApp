package com.osmar.themoviedbapp.ui.menu.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepository
import com.osmar.themoviedbapp.ui.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val movieDaoRepository: MovieDaoRepository
): ViewModel() {

    val items = movieDaoRepository.fetchLocalMoviesList()

    fun removeToFavorite(movie : MovieModel){
        viewModelScope.launch {
            movieDaoRepository.deleteLocalMovie(movie)
                .collect{}
        }
    }

}