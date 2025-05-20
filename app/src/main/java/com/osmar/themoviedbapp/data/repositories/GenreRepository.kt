package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.data.network.ApiResponseHandler
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.data.network.ResultResponse
import javax.inject.Inject

class GenreRepository @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseHandler: ApiResponseHandler
) {
    suspend fun getGenreList() : ResultResponse<GenresModel>{
        apiService.getGenreList().let { response ->
            return apiResponseHandler.handleApiResponse(response)
        }
    }
}