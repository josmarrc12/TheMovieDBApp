package com.osmar.themoviedbapp

import com.osmar.themoviedbapp.data.network.ApiResponseHandler
import com.osmar.themoviedbapp.data.network.ApiService
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.repositories.GenreRepositoryImpl
import com.osmar.themoviedbapp.data.response.GeneralResponseMoviesModel
import com.osmar.themoviedbapp.data.response.GenreModel
import com.osmar.themoviedbapp.data.response.GenresModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Response

class GenreRepositoryTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Test
    fun getGenreListGetResultResponse() = runTest(coroutineRule.testDispatcher){
        class FakeApi() : ApiService{
            override fun getGenreListOld(apiKey: String): Call<GenresModel> {
                TODO("Not yet implemented")
            }

            override suspend fun getMovieNowPlaying(
                apiKey: String,
                page: Int
            ): Response<GeneralResponseMoviesModel> {
                TODO("Not yet implemented")
            }

            override suspend fun getMoviesPaging(
                apiKey: String,
                page: Int
            ): GeneralResponseMoviesModel {
                TODO("Not yet implemented")
            }

            override suspend fun getMoviesPaging(
                typeList: String,
                apiKey: String,
                page: Int
            ): GeneralResponseMoviesModel {
                TODO("Not yet implemented")
            }

            override suspend fun getGenreList(apiKey: String): Response<GenresModel> {
                return Response.success(
                    GenresModel(listOf(
                        GenreModel(1,"Horror"),
                        GenreModel(2,"Comedy"),
                        GenreModel(3,"Romance"),
                    ))
                )
            }
        }

        val genreRepository = GenreRepositoryImpl(FakeApi(), ApiResponseHandler())

        val genreListResult = genreRepository.getGenreList()

        assert(genreListResult is ResultResponse.Success)
        assertEquals(3,(genreListResult as ResultResponse.Success).data.genres.size)
    }
}