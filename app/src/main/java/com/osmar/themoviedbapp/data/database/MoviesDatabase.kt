package com.osmar.themoviedbapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.osmar.themoviedbapp.data.database.dao.MoviesDao
import com.osmar.themoviedbapp.data.database.entity.MoviesEntity
import com.osmar.themoviedbapp.di.LongListTypeConverter

@Database(entities =[ MoviesEntity::class] , version = 1, exportSchema = false)
@TypeConverters(value = [LongListTypeConverter::class])
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun moviesDao(): MoviesDao
}