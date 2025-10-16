package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.database.dao.MoviesDao
import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.ui.home.models.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MovieDaoRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : MovieDaoRepository{
    override fun fetchLocalMovieDetails(id: Long): Flow<MoviesEntity?> = flow {
        emit(moviesDao.getMovieDetail(id))
    }.flowOn(Dispatchers.IO)

    override fun fetchLocalMoviesList(): Flow<List<MoviesEntity>> = flow {
        emit(moviesDao.getMovieList())
    }.flowOn(Dispatchers.IO)

    override fun insertLocalMovie(movieModel: MovieModel) = flow {
        emit(moviesDao.insert(movieModel.toEntity()))
    }.flowOn(Dispatchers.IO)

    override fun deleteLocalMovie(movieModel: MovieModel) = flow {
        emit(moviesDao.delete(movieModel.toEntity()))
    }.flowOn(Dispatchers.IO)
}