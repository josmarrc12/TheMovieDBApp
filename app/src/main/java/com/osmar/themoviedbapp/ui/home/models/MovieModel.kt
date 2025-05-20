package com.osmar.themoviedbapp.ui.home.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class MovieModel(
    val posterPath : String?,
    val title : String,
    val voteAverage : Double,
    val releaseDate : String,
    val genreIDs : List<Long>,
    val description : String
) : Parcelable
