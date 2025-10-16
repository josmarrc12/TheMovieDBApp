package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieDaoRepository {
    fun fetchLocalMovieDetails(id : Long): Flow<MoviesEntity?>

    fun fetchLocalMoviesList(): Flow<List<MoviesEntity>>

    fun insertLocalMovie(movieModel: MovieModel): Flow<Unit>

    fun deleteLocalMovie(movieModel: MovieModel): Flow<Unit>
}