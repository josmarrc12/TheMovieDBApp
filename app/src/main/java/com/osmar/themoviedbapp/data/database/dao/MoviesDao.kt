package com.osmar.themoviedbapp.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie : MoviesEntity)

    @Update
    suspend fun update(movie : MoviesEntity)

    @Delete
    suspend fun delete(movie : MoviesEntity)

    @Query("SELECT * FROM MoviesEntity WHERE id = :idMovie")
    suspend fun getMovieDetail(idMovie : Long) : MoviesEntity?

    @Query("SELECT * FROM MoviesEntity ORDER BY releaseDate DESC")
    fun getMovieList() : Flow<List<MoviesEntity>>
}