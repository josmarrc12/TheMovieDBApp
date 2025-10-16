package com.osmar.themoviedbapp.ui.home.screen


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.osmar.themoviedbapp.R
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.home.HomeViewModel
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.utils.ImageSize
import com.osmar.themoviedbapp.utils.Utils
import com.osmar.themoviedbapp.utils.Utils.provideColor
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navigateToConfig:() -> Unit,
    navigateToDetails:(MovieModel) -> Unit){
        LaunchedEffect(Unit){
    }
    val movieListState by viewModel.movieList.collectAsState()
    when(movieListState){
        UiState.Start -> {}
        is UiState.Error -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.error),
                contentAlignment = Alignment.Center
            ){
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = stringResource(R.string.error),
                    color = MaterialTheme.colorScheme.onError,
                )
            }
        }
        UiState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ){
                CircularProgressIndicator(
                    modifier =  Modifier.size(64.dp),
                    color = MaterialTheme.colorScheme.onError
                )
            }
        }
        is UiState.Success -> {
            val movieList = (movieListState as UiState.Success<Flow<PagingData<MovieModel>>>)
                .data.collectAsLazyPagingItems()

            when{
                //starting loading
                movieList.loadState.refresh is LoadState.Loading && movieList.itemCount == 0 ->{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier =  Modifier.size(64.dp),
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                //not data
                movieList.loadState.refresh is LoadState.NotLoading && movieList.itemCount == 0 ->{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(R.string.no_information),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    }
                }
                //Error
                movieList.loadState.hasError -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.error),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ){

                        Text(
                            modifier = Modifier.padding(4.dp),
                            text = stringResource(R.string.network_error),
                            color = MaterialTheme.colorScheme.onError,
                        )

                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                viewModel.fetchMovies(View.NOW_PLAYING)
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Refresh,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onError
                            )
                        }

                    }
                }
                else ->{
                    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
                    Scaffold(
                        modifier = Modifier
                            .nestedScroll(scrollBehavior.nestedScrollConnection),
                        topBar = { HomeMainBar(scrollBehavior, viewModel){
                            navigateToConfig()
                        }
                                 },
                        bottomBar = {HomeNavigationBar(viewModel)}

                    ){ innerPadding ->
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            contentAlignment = Alignment.BottomCenter
                        ) {

                            HomeGridPaging(movieList){
                                    movie -> navigateToDetails(movie)
                            }
                            if (movieList.loadState.append is LoadState.Loading){
                                CircularProgressIndicator(
                                    modifier =  Modifier.size(32.dp),
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}


@Composable
fun HomeGridPaging(
    movies : LazyPagingItems<MovieModel>,
    navigateToDetails: (MovieModel)->Unit,
){

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier
            .fillMaxSize()
//            .padding(paddingValues)
            .background(color = MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(movies.itemCount){
            movies[it]?.let { movie ->

                if (!movie.posterPath.isNullOrBlank()){
                    ItemMovie(movie){
                        navigateToDetails(movie)
                    }
                }
            }
        }
    }
}

@Composable
fun ItemMovie(movie : MovieModel, navigateToDetails: (MovieModel)->Unit){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    navigateToDetails(movie)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = Utils.getUrlImage(ImageSize.BIG.size, movie.posterPath),
                contentDescription = ""
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeMainBar(
    scrollBehavior: TopAppBarScrollBehavior,
    viewModel: HomeViewModel,
    navigateToConfig : ()->Unit){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onSurface,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = when(viewModel.activeView.collectAsState().value){
                    View.NOW_PLAYING ->{stringResource(R.string.now_playing)}
                    View.POPULAR ->{stringResource(R.string.popular)}
                    View.TOP_RATED ->{stringResource(R.string.top_rated)}
                    View.UPCOMING ->{ stringResource(R.string.upcoming)}
                },
                style = MaterialTheme.typography.titleMedium
            )
        },
        navigationIcon = {
//            Icon(
//                imageVector = Icons.Filled.Menu,
//                contentDescription = "",
//                tint = MaterialTheme.colorScheme.onPrimary
//            )
        },
        actions = {
            IconButton(
                onClick = {
                    navigateToConfig()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun HomeNavigationBar(viewModel: HomeViewModel){
    val view = viewModel.activeView.collectAsState()
    val activeColor = MaterialTheme.colorScheme.primary
    val passiveColor = MaterialTheme.colorScheme.onSurface
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                viewModel.fetchMovies(View.NOW_PLAYING)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "",
                tint = provideColor(view.value == View.NOW_PLAYING,activeColor,passiveColor)
            )
        }

        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                viewModel.fetchMovies(View.POPULAR)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "",
                tint = provideColor(view.value == View.POPULAR,activeColor,passiveColor)
            )
        }

        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                viewModel.fetchMovies(View.TOP_RATED)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = provideColor(view.value == View.TOP_RATED,activeColor,passiveColor)
            )
        }

        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {
                viewModel.fetchMovies(View.UPCOMING)
            }
        ) {
            Icon(
                imageVector = Icons.Filled.DateRange,
                contentDescription = "",
                tint = provideColor(view.value == View.UPCOMING,activeColor,passiveColor)
            )
        }
    }
}




enum class View(val key:String) {
    NOW_PLAYING("now_playing"),
    TOP_RATED("top_rated"),
    POPULAR("popular"),
    UPCOMING("upcoming")
}
