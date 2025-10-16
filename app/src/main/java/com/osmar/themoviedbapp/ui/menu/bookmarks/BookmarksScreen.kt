package com.osmar.themoviedbapp.ui.menu.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.osmar.themoviedbapp.R
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.common.CommonHeader
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.utils.ImageSize
import com.osmar.themoviedbapp.utils.Utils

@Composable
fun BookmarksScreen(
    viewModel: BookmarksViewModel = hiltViewModel(),
    navigationBack: () ->  Unit
){
    val movieListState by viewModel.movieList.collectAsState()
    when(movieListState){
        UiState.Start ->{}
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
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
        is UiState.Success<*> -> {
            val movieList = (movieListState as UiState.Success<List<MovieModel>>).data
            Column(
                modifier = Modifier.fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                CommonHeader(title = stringResource(R.string.bookmarks), isNavigation = true) {
                    navigationBack()
                }
                BookmarkContent(movieList, viewModel)
            }

        }
    }
}

@Composable
fun BookmarkContent(movieList: List<MovieModel>, viewModel: BookmarksViewModel) {
    LazyColumn(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(bottom = 64.dp)
    ) {
        items(movieList){ movie ->
            Card(
                modifier = Modifier
                    .height(128.dp)
                    .padding(4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                )
            ){
                Box(
                    modifier = Modifier.fillMaxSize()
                ){
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            modifier = Modifier.padding(4.dp),
                            model = Utils.getUrlImage(ImageSize.BIG.size, movie.posterPath),
                            contentDescription = ""
                        )
                        Column(
                            modifier = Modifier.fillMaxSize()
                                .padding(end = 4.dp),
                        ) {
                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = movie.title,
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = MaterialTheme.typography.titleMedium,
                            )

                            Text(
                                modifier = Modifier.padding(4.dp),
                                text = movie.releaseDate.substring(0,4),
                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.BottomEnd),
                        onClick = {
                            viewModel.removeToFavorite(movie)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.BookmarkRemove,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }


            }

        }
    }
}
