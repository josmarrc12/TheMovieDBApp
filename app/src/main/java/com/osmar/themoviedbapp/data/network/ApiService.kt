package com.osmar.themoviedbapp.data.network

import com.osmar.themoviedbapp.data.ApiConstants
import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.data.response.GeneralResponseMoviesModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("genre/movie/list")
    fun getGenreListOld(@Header(ApiConstants.HEADER_AUTH) apiKey : String = ApiConstants.API_KEY): Call<GenresModel>

    @GET("movie/now_playing")
    suspend fun getMovieNowPlaying(@Header(ApiConstants.HEADER_AUTH) apiKey: String = ApiConstants.API_KEY, @Query("page") page: Int): Response<GeneralResponseMoviesModel>

    @GET("movie/{type_list}")
    suspend fun getMoviesPaging(
        @Path("type_list") typeList: String,
        @Header(ApiConstants.HEADER_AUTH) apiKey: String = ApiConstants.API_KEY,
        @Query("page") page: Int,
        @Query("language") language: String,
        ):GeneralResponseMoviesModel

    @GET("genre/movie/list")
    suspend fun getGenreList(
        @Header(ApiConstants.HEADER_AUTH) apiKey : String = ApiConstants.API_KEY,
        @Query("language") language: String,
        ): Response<GenresModel>
}