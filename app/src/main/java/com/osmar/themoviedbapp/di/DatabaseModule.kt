package com.osmar.themoviedbapp.di

import android.app.Application
import androidx.room.ProvidedTypeConverter
import androidx.room.Room
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.osmar.themoviedbapp.data.database.MoviesDatabase
import com.osmar.themoviedbapp.data.database.dao.MoviesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
        longListTypeConverter: LongListTypeConverter
    ) : MoviesDatabase{
        return Room
            .databaseBuilder(application, MoviesDatabase::class.java, "Movies.db")
            .fallbackToDestructiveMigration(false)
            .addTypeConverter(longListTypeConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideMoviesDao(appDatabase: MoviesDatabase) : MoviesDao{
        return appDatabase.moviesDao()
    }
    @Provides
    @Singleton
    fun provideLongListTypeConverter() : LongListTypeConverter{
        return LongListTypeConverter()
    }
}

@ProvidedTypeConverter
class LongListTypeConverter {
    @TypeConverter
    fun toLongList(value: String?): List<Long>? {
        val listType = object : TypeToken<List<Long>>() {}.type
       return value?.let { Gson().fromJson(it, listType) }
    }

    @TypeConverter
    fun fromLongList(genreIDs : List<Long>?) : String? {
        return genreIDs?.let { Gson().toJson(it) }
    }
}