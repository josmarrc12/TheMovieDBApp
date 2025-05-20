package com.osmar.themoviedbapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.osmar.themoviedbapp.data.response.MovieResponse
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.repositories.GenreRepository
import com.osmar.themoviedbapp.data.repositories.MovieListRepository
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.ui.home.screen.View
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieListRepository: MovieListRepository,
    private val genreRepository: GenreRepository
): ViewModel() {

    val movies: Flow<PagingData<MovieModel>> = movieListRepository.getMovieListPagination()

    private val _movieList = MutableStateFlow<UiState<List<MovieModel>>>(UiState.Start)
    val movieList = _movieList.asStateFlow()

    private val _activeView = MutableStateFlow<UiState<View>>(UiState.Start)
    val activeView = _activeView.asStateFlow()

    private val _genreNameList = MutableStateFlow<UiState<List<String>>>(UiState.Start)
    val genreNameList = _genreNameList.asStateFlow()

    init {
        fetchNowPlayingMovies()
    }

    private fun fetchNowPlayingMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            _movieList.value = UiState.Loading
            movieListRepository.getMovieList().let { resultResponse ->
                when(resultResponse){
                    is ResultResponse.Error -> _movieList.value = UiState.Error(resultResponse.message)
                    is ResultResponse.Success -> {
                        _movieList.value = UiState.Success(
                            responseModelToUIModel(resultResponse.data.movieList)
                        )
                    }
                }
            }
        }
    }

    private fun responseModelToUIModel(movieList : List<MovieResponse>)
    :List<MovieModel>{
       return movieList.map { movie ->
            MovieModel(
                posterPath = movie.posterPath,
                title = movie.title,
                voteAverage = movie.voteAverage,
                releaseDate = movie.releaseDate,
                genreIDs = movie.genreIDS,
                description = movie.overview
            )
        }
    }

    fun fetchGenresName(genresIDs : List<Long>){
        viewModelScope.launch(Dispatchers.IO) {
            _genreNameList.value = UiState.Loading
            genreRepository.getGenreList().let { resultResponse ->
                when(resultResponse){
                    is ResultResponse.Error -> _genreNameList.value = UiState.Error(resultResponse.message)
                    is ResultResponse.Success ->{
                        val genreNames = resultResponse.data.genres.filter {
                            it.id in genresIDs
                        }.map { it.name }
                        _genreNameList.value = UiState.Success(
                            genreNames
                        )
                    }
                }
            }
        }
    }




}