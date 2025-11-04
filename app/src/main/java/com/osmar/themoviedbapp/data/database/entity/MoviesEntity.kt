package com.osmar.themoviedbapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.osmar.themoviedbapp.ui.MovieModel

@Entity
data class MoviesEntity (
    @PrimaryKey val id: Long,
    val posterPath : String?,
    val title : String,
    val voteAverage : Double,
    val releaseDate : String,
    val genreIDs : List<Long>,
    val description : String
)

fun MoviesEntity.toModel() : MovieModel {
    return MovieModel(
        id = id,
        posterPath = posterPath,
        title = title,
        voteAverage = voteAverage,
        releaseDate = releaseDate,
        genreIDs = genreIDs,
        description = description
    )
}