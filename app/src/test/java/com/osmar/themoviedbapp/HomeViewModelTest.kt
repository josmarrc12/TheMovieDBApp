package com.osmar.themoviedbapp

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.osmar.themoviedbapp.data.network.ResultResponse
import com.osmar.themoviedbapp.data.repositories.GenreRepository
import com.osmar.themoviedbapp.data.repositories.MovieListRepository
import com.osmar.themoviedbapp.data.repositories.MovieListRepositoryImpl.Companion.MAX_ITEMS
import com.osmar.themoviedbapp.data.repositories.MovieListRepositoryImpl.Companion.PREFETCH_ITEMS
import com.osmar.themoviedbapp.data.response.GenreModel
import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.HomeViewModel
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class HomeViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    @Before
    fun setup(){

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testMovieListStatusIsSuccess() = runTest(coroutineRule.testDispatcher){

       val fakeMovieList = listOf(
            MovieModel("","",5.0,"", listOf(1,2,3),""),
            MovieModel("","",5.0,"", listOf(1,2,3),""),
            MovieModel("","",5.0,"", listOf(1,2,3),""),
        )

        class FakePaging : PagingSource<Int, MovieModel>(){
            override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
                return state.anchorPosition
            }

            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {
                return LoadResult.Page(
                    data = fakeMovieList,
                    prevKey = null,
                    nextKey = null
                )

            }
        }

        class FakeMovieListRepository : MovieListRepository{
            override fun getMovieListPagination(typeList: String): Flow<PagingData<MovieModel>> {
                return Pager(config = PagingConfig(
                    pageSize = MAX_ITEMS,
                    prefetchDistance = PREFETCH_ITEMS
                ),
                    pagingSourceFactory = {
                        FakePaging()
                    }).flow
            }
        }

        class FakeGenreRepository : GenreRepository{
            override suspend fun getGenreList(): ResultResponse<GenresModel> {
                return ResultResponse.Success(GenresModel(listOf(
                    GenreModel(1,""),
                    GenreModel(2,""),
                    GenreModel(3,""),
                )))
            }
        }

        val viewModel = HomeViewModel(FakeMovieListRepository(), FakeGenreRepository())
        advanceUntilIdle()
        assert(viewModel.movieList.value is UiState.Success)

    }
}