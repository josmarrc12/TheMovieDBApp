package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import com.osmar.themoviedbapp.ui.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieDaoRepository {
    fun fetchLocalMovieDetails(id : Long): Flow<MoviesEntity?>

    fun fetchLocalMoviesList(): Flow<List<MovieModel>>

    fun insertLocalMovie(movieModel: MovieModel): Flow<Unit>

    fun deleteLocalMovie(movieModel: MovieModel): Flow<Unit>
}