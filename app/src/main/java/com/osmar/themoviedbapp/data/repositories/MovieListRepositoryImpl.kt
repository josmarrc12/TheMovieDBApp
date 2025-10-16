package com.osmar.themoviedbapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.data.paging.MoviesPagingSource
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepositoryImpl @Inject constructor(
    private val apiService: ApiService
) : MovieListRepository{
    companion object{
        const val MAX_ITEMS = 21
        const val PREFETCH_ITEMS = 9
    }

    override fun getMovieListPagination(typeList: String, language: String): Flow<PagingData<MovieModel>> {
        return Pager(config = PagingConfig(
            pageSize = MAX_ITEMS,
            prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                MoviesPagingSource(apiService, typeList, language)
            }).flow
    }
}