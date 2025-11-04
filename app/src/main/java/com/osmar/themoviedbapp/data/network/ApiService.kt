package com.osmar.themoviedbapp.data.network

import com.osmar.themoviedbapp.data.response.GeneralResponseMoviesModel
import com.osmar.themoviedbapp.data.response.GenresModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{type_list}")
    suspend fun getMoviesPaging(
        @Path("type_list") typeList: String,
        @Query("page") page: Int,
        @Query("language") language: String,
        ):GeneralResponseMoviesModel

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Query("language") language: String,
        ): Response<GenresModel>
}