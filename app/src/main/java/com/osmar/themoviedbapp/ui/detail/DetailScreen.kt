package com.osmar.themoviedbapp.ui.detail


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.osmar.themoviedbapp.R
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.common.CommonHeader
import com.osmar.themoviedbapp.ui.MovieModel
import com.osmar.themoviedbapp.utils.ImageSize
import com.osmar.themoviedbapp.utils.Utils
import com.osmar.themoviedbapp.utils.Utils.provideColor
import java.util.Locale

@Composable
fun DetailScreen(
    viewModel: DetailViewModel = hiltViewModel(),
    movie : MovieModel,
    navigationBack: () -> Unit
){
    LaunchedEffect(Unit) {
        viewModel.fetchGenresName(movie.genreIDs)
        viewModel.isFavorite(movie.id)
    }
    val genresName by viewModel.genreNameList.collectAsState()
    when(genresName){
        is UiState.Error -> {
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
                        viewModel.fetchGenresName(movie.genreIDs)
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
        UiState.Start -> {
            Text("")}
        is UiState.Success ->{
            val genres = (genresName as UiState.Success<List<String>>).data
            DetailContent(movie,genres, viewModel) {
                navigationBack()
            }
        }
    }

}

@Composable
fun DetailContent(
    movie : MovieModel,
    genresList : List<String>,
    viewModel: DetailViewModel,
    navigationBack: () -> Unit
    ){

    val isFavorite = viewModel.isFavorite.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CommonHeader(title = stringResource(R.string.details), true){
            navigationBack()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = Utils.getUrlImage(ImageSize.BIG.size, movie.posterPath),
                contentDescription = ""
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .weight(1f)
        ) {
            val scrollStateDescription = rememberScrollState()
            Column(
                modifier = Modifier
                    .padding(bottom = 36.dp)
                    .verticalScroll(scrollStateDescription)
            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = movie.title,
                        color = MaterialTheme.colorScheme.primary,
                        textAlign = TextAlign.Start,
                        style = MaterialTheme.typography.titleLarge
                    )

                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ){
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.star),
                                contentDescription = ""
                            )

                            Text(
                                text = String.format(Locale.US,"%.1f",movie.voteAverage),
                                color = MaterialTheme.colorScheme.secondary,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = movie.releaseDate.substring(0,4),
                        color = MaterialTheme.colorScheme.secondary
                    )
                    IconButton(
                        onClick = {
                            if (isFavorite.value){
                                viewModel.removeToFavorite(movie)
                            }else{
                                viewModel.addToFavorite(movie)
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.bookmark_hearth),
                            contentDescription = "",
                            tint = provideColor(
                                isFavorite.value,
                                MaterialTheme.colorScheme.tertiary,
                                MaterialTheme.colorScheme.onSurface)
                        )
                    }
//                    Image(
//                        modifier = Modifier.clickable { Log.d("HomeViewModel", "hola") },
//                        painter = painterResource(id = R.drawable.bookmark_hearth),
//                        contentDescription = ""
//                    )
                }
                val scrollStateGenre = rememberScrollState()
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollStateGenre)
                ) {
                    genresList.forEach{ genreID ->
                        GenreItem(genreID)
                    }
                }

                Row{
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = movie.description,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Start
                    )
                }
            }

        }
    }
}

@Composable
fun GenreItem(genreName : String){
    Card(
        modifier = Modifier
            .padding(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiary
        )
    ){
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = genreName,
            color = MaterialTheme.colorScheme.onTertiary,
        )
    }
}