package com.osmar.themoviedbapp.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.osmar.themoviedbapp.data.response.GeneralResponseMoviesModel
import com.osmar.themoviedbapp.data.network.ApiResponseHandler
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.paging.MoviesPagingSource
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseHandler: ApiResponseHandler
) {
    companion object{
        const val MAX_ITEMS = 15
        const val PREFETCH_ITEMS = 6
    }

    suspend fun getMovieList(page: Int = 1) : ResultResponse<GeneralResponseMoviesModel>{
        apiService.getMovieNowPlaying(page = page).let { response ->
            return apiResponseHandler.handleApiResponse(response)
        }
    }

    fun getMovieListPagination():Flow<PagingData<MovieModel>>{
        return Pager(config = PagingConfig(
            pageSize = MAX_ITEMS,
            prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                MoviesPagingSource(apiService)
            }).flow
    }
}