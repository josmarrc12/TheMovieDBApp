package com.osmar.themoviedbapp.data.repositories

import androidx.paging.PagingData
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {

    fun getMovieListPagination(
        typeList: String,
        language: String,
    ):Flow<PagingData<MovieModel>>

}