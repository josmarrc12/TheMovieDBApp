package com.osmar.themoviedbapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import com.osmar.themoviedbapp.ui.home.screen.DetailScreen
import com.osmar.themoviedbapp.ui.home.screen.HomeScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


@Serializable
object Home
@Serializable
data class Details(val movie: MovieModel)

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Home> {
            HomeScreen{ movie -> navController.navigate(Details(movie =movie)) }
        }
        composable<Details>(typeMap = mapOf(typeOf<MovieModel>() to objectToNavType<MovieModel>(isNullableAllowed = false))) { backStackEntry ->
            val details: Details = backStackEntry.toRoute()
            DetailScreen(movie = details.movie){
                navController.navigateUp()
            }
        }
    }
}

