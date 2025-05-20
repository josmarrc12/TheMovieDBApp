package com.osmar.themoviedbapp.ui.home.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage
import com.osmar.themoviedbapp.R
import com.osmar.themoviedbapp.ui.UiState
import com.osmar.themoviedbapp.ui.common.CommonHeader
import com.osmar.themoviedbapp.ui.home.HomeViewModel
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.utils.ImageSize
import com.osmar.themoviedbapp.utils.Utils

@Composable
fun DetailScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    movie : MovieModel,
    navigationBack: () -> Unit
    ){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        CommonHeader(title = "Details", true){
            navigationBack()
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
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
            Column {
                Row(
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .weight(1f),
                        text = movie.title,
                        color = MaterialTheme.colorScheme.onPrimary,
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
                                text = "(${movie.voteAverage})",
                                color = MaterialTheme.colorScheme.onPrimary,
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
                Row {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 16.dp),
                        text = movie.releaseDate.substring(0,4),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                val scrollStateGenre = rememberScrollState()
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollStateGenre)
                ) {
                    viewModel.fetchGenresName(movie.genreIDs)
                    val genresName by viewModel.genreNameList.collectAsState()
                    when(genresName){
                        is UiState.Error -> GenreItem((genresName as UiState.Error).message)
                        UiState.Loading -> GenreItem("Loading")
                        UiState.Start -> {}
                        is UiState.Success ->{
                            (genresName as UiState.Success<List<String>>).data.forEach{ genreID ->
                                GenreItem(genreID)
                            }
                        }
                    }

                }
                val scrollStateDescription = rememberScrollState()
                Row{
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .verticalScroll(scrollStateDescription),
                        text = movie.description,
                        color = MaterialTheme.colorScheme.onPrimary,
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
            containerColor = MaterialTheme.colorScheme.primary
        )
    ){
        Text(
            modifier = Modifier
                .padding(4.dp),
            text = genreName,
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}