package com.osmar.themoviedbapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.osmar.themoviedbapp.core.RetrofitHelper
import com.osmar.themoviedbapp.data.response.GenresModel
import com.osmar.themoviedbapp.ui.theme.TheMovieDBAppTheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val apiService = RetrofitHelper.apiService
        val call = apiService.getGenreListOld()

        call.enqueue(object : Callback<GenresModel> {
            override fun onResponse(call: Call<GenresModel>, response: Response<GenresModel>){
                if (response.isSuccessful){
                    val genreList = response.body()
                    Log.d("genreList",genreList.toString())
                    Log.d("Response",response.code().toString())
                }else{
                    Log.e("Api Error", response.message())
                }
            }

            override fun onFailure(p0: Call<GenresModel>, p1: Throwable) {
                Log.e("Api Failure", p1.message ?: "Unknown error")
            }
        })

        enableEdgeToEdge()
        setContent {
            TheMovieDBAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

