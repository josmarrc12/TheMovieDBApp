package com.osmar.themoviedbapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import okio.IOException
import javax.inject.Inject

class MoviesPagingSource @Inject constructor(
    private val apiService: ApiService
) : PagingSource<Int, MovieModel>(){

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
        return try {
            val page = params.key ?: 1
            val response = apiService.getMoviesPaging(page = page)
            val movieList = response.movieList

            val prevKey = if (page > 1) page - 1 else null
            val nextKey = if (response.totalPages > page) page + 1 else null

            LoadResult.Page(
                data = movieList.map{ movie ->
                    MovieModel(
                        posterPath = movie.posterPath,
                        title = movie.title,
                        voteAverage = movie.voteAverage,
                        releaseDate = movie.releaseDate,
                        genreIDs = movie.genreIDS,
                        description = movie.overview)
                },
                prevKey = prevKey,
                nextKey = nextKey
            )
        }catch (e: IOException){
            LoadResult.Error(e)
        }
    }

}