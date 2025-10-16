package com.osmar.themoviedbapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import com.osmar.themoviedbapp.data.repositories.GenreRepository
import com.osmar.themoviedbapp.data.repositories.GenreRepositoryImpl
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepository
import com.osmar.themoviedbapp.data.repositories.MovieDaoRepositoryImpl
import com.osmar.themoviedbapp.data.repositories.MovieListRepository
import com.osmar.themoviedbapp.data.repositories.MovieListRepositoryImpl
import com.osmar.themoviedbapp.datastore.LanguagePreferences
import com.osmar.themoviedbapp.utils.languagePreferencesDataStore
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Suppress("unused")
@Module
@InstallIn(SingletonComponent::class)
internal interface DataModule {
    @Binds
    fun bindsMovieListRepository(movieListRepositoryImpl: MovieListRepositoryImpl): MovieListRepository

    @Binds
    fun bindsGenreRepository(genreRepositoryImpl: GenreRepositoryImpl) : GenreRepository

    @Binds
    fun bindsMovieDetailRepository(movieDetailsRepositoryImpl: MovieDaoRepositoryImpl) : MovieDaoRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule{
    @Provides
    @Singleton
    fun providesLanguageDataStore(
        @ApplicationContext context: Context
    ): DataStore<LanguagePreferences> = context.languagePreferencesDataStore
}

