package com.osmar.themoviedbapp.data

import com.osmar.themoviedbapp.BuildConfig


object ApiConstants{
    const val BASE_URL_PATH = "https://api.themoviedb.org/3/"
    const val BASE_URL_PATH_IMAGE = "https://image.tmdb.org/t/p/"
    const val HEADER_AUTH = "Authorization"
    const val API_KEY = BuildConfig.API_KEY
}