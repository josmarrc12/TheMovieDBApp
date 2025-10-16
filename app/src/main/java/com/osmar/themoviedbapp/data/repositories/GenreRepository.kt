package com.osmar.themoviedbapp.data.repositories

import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.data.network.ResultResponse

interface GenreRepository{
    suspend fun getGenreList(language : String) : ResultResponse<GenresModel>
}