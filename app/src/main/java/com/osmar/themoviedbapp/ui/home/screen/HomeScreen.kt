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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil3.compose.AsyncImage
import com.osmar.themoviedbapp.ui.common.CommonHeader
import com.osmar.themoviedbapp.ui.home.HomeViewModel
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.utils.ImageSize
import com.osmar.themoviedbapp.utils.Utils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel(), navigateToDetails:(MovieModel) -> Unit){
    val movieList = viewModel.movies.collectAsLazyPagingItems()
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
                   color = Color.White
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
                InfoMessage("No information")
            }
        }
        //Error
        movieList.loadState.hasError -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ){
                InfoMessage("Error")
            }
        }
        else ->{
            val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
            Scaffold(
                modifier = Modifier
                    .nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = { HomeMainBar(scrollBehavior) },
                bottomBar = {HomeNavigationBar(View.NOW_PLAYING)}

            ) { innerPadding ->
                HomeGridPaging(movieList, innerPadding){
                        movie -> navigateToDetails(movie)
                }
                if (movieList.loadState.append is LoadState.Loading){
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = MaterialTheme.colorScheme.background),
                        contentAlignment = Alignment.Center
                    ){
                        CircularProgressIndicator(
                            modifier =  Modifier.size(64.dp),
                            color = Color.White
                        )
                    }
                }
            }

        }
    }
}


@Composable
fun HomeGrid(movies : List<MovieModel>, navigateToDetails: (MovieModel)->Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background)
    ) {
        CommonHeader(title = "TheMovieDBAPP"){}
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            items(movies){ movie ->
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
fun HomeGridPaging(
    movies : LazyPagingItems<MovieModel>,
    paddingValues : PaddingValues,
    navigateToDetails: (MovieModel)->Unit
){
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//            .background(color = MaterialTheme.colorScheme.background)
//    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
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
//    }
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
                .clickable { navigateToDetails(movie) },
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
fun HomeMainBar(scrollBehavior: TopAppBarScrollBehavior){
    CenterAlignedTopAppBar(
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        title = {
            Text(
                text = "TheMovieDBApp"
            )
        },
        navigationIcon = {
            Icon(
                imageVector = Icons.Filled.Menu,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = {}
            ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun HomeNavigationBar(currentView : View){
    val activeColor = MaterialTheme.colorScheme.secondary
    val passiveColor = MaterialTheme.colorScheme.onPrimary
    BottomAppBar(
        containerColor = MaterialTheme.colorScheme.background
    ) {
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.ThumbUp,
                contentDescription = "",
                tint = getColorView(currentView == View.POPULAR,activeColor,passiveColor)
            )
        }
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.PlayArrow,
                contentDescription = "",
                tint = getColorView(currentView == View.NOW_PLAYING,activeColor,passiveColor)
            )
        }
        IconButton(
            modifier = Modifier
                .weight(1f),
            onClick = {}
        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "",
                tint = getColorView(currentView == View.TOP_RATED,activeColor,passiveColor)
            )
        }
    }
}

private fun getColorView(activeView : Boolean, activeColor : Color, passiveColor : Color) : Color{
    return if (activeView) activeColor else passiveColor
}

@Composable
fun InfoMessage(message : String){
    Text(
        modifier = Modifier.padding(4.dp),
        text = message,
        color = MaterialTheme.colorScheme.onPrimary,
    )
}

enum class View {NOW_PLAYING,TOP_RATED,POPULAR}
