package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.database.dao.MoviesDao
import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import com.osmar.themoviedbapp.data.database.entity.toModel
import com.osmar.themoviedbapp.ui.MovieModel
import com.osmar.themoviedbapp.ui.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieDaoRepositoryImpl @Inject constructor(
    private val moviesDao: MoviesDao
) : MovieDaoRepository{
    override fun fetchLocalMovieDetails(id: Long): Flow<MoviesEntity?> = flow {
        emit(moviesDao.getMovieDetail(id))
    }.flowOn(Dispatchers.IO)

//    override fun fetchLocalMoviesList(): Flow<List<MoviesEntity>> = flow {
//        emit(moviesDao.getMovieList())
//    }.flowOn(Dispatchers.IO)

    override fun fetchLocalMoviesList(): Flow<List<MovieModel>>
    = moviesDao.getMovieList().map { listMoviesEntities ->
        listMoviesEntities.map { movieEntity ->
            movieEntity.toModel()
        }
    }



    override fun insertLocalMovie(movieModel: MovieModel) = flow {
        emit(moviesDao.insert(movieModel.toEntity()))
    }.flowOn(Dispatchers.IO)

    override fun deleteLocalMovie(movieModel: MovieModel) = flow {
        emit(moviesDao.delete(movieModel.toEntity()))
    }.flowOn(Dispatchers.IO)
}