package com.osmar.themoviedbapp.data.response

import com.google.gson.annotations.SerializedName

data class GeneralResponseMoviesModel(
    val dates: Dates,
    val page: Long,
    @SerializedName("results") val movieList: List<MovieResponse>,
    @SerializedName("total_pages") val totalPages: Long,
    @SerializedName("total_results") val totalResults: Long
)