package com.osmar.themoviedbapp.ui.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.osmar.themoviedbapp.ui.MovieModel
import com.osmar.themoviedbapp.ui.menu.MenuScreen
import com.osmar.themoviedbapp.ui.detail.DetailScreen
import com.osmar.themoviedbapp.ui.home.HomeScreen
import com.osmar.themoviedbapp.ui.menu.bookmarks.BookmarksScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf


@Serializable
object Home
@Serializable
data class Details(val movie: MovieModel)
@Serializable
object Menu
@Serializable
object Bookmarks

@Composable
fun NavigationWrapper(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Home){
        composable<Home> {
            HomeScreen (
                navigateToDetails = { movie -> navController.navigate(Details(movie = movie)) },
                navigateToConfig =  { navController.navigate(Menu)})
        }
        composable<Details>(typeMap = mapOf(typeOf<MovieModel>() to objectToNavType<MovieModel>(isNullableAllowed = false))) { backStackEntry ->
            val details: Details = backStackEntry.toRoute()
            DetailScreen(movie = details.movie){
                navController.navigateUp()
            }
        }
        composable<Menu> {
            MenuScreen(
                navigationBack = {navController.navigateUp()},
                navigationReset = {
                    navController.navigate(Home){
                        popUpTo(Home){
                            inclusive = true
                        }
                    }
                },
                navigateToBookmarks = {
                    navController.navigate(Bookmarks)
                }
            )
        }
        composable<Bookmarks> {
            BookmarksScreen(
                navigationBack = { navController.navigateUp() },
                navigateToDetails = { movie -> navController.navigate(Details(movie = movie)) },
            )

        }
    }
}

