package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.data.network.ApiResponseHandler
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.data.network.ResultResponse
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val apiResponseHandler: ApiResponseHandler
) : GenreRepository{
    override suspend fun getGenreList(language : String) : ResultResponse<GenresModel>{
        apiService.getGenreList(language = language).let { response ->
            return apiResponseHandler.handleApiResponse(response)
        }
    }
}