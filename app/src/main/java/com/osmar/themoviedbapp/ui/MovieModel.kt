package com.osmar.themoviedbapp.ui

import android.os.Parcelable
import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable


@Serializable
@Parcelize
data class MovieModel(
    val id : Long,
    val posterPath : String?,
    val title : String,
    val voteAverage : Double,
    val releaseDate : String,
    val genreIDs : List<Long>,
    val description : String
) : Parcelable

fun MovieModel.toEntity() : MoviesEntity{
    return MoviesEntity(
        id = id,
        posterPath = posterPath,
        title = title,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        genreIDs = genreIDs,
        description = description
    )
}