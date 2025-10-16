package com.osmar.themoviedbapp.ui.home

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.view.WindowCompat
import com.osmar.themoviedbapp.ui.nav.NavigationWrapper
import com.osmar.themoviedbapp.ui.theme.TheMovieDBAppTheme
import com.osmar.themoviedbapp.utils.languagePreferencesDataStore
import com.osmar.themoviedbapp.utils.updateLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking


@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val isDarkTheme = resources.configuration.uiMode and
                Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        val barColor = if(isDarkTheme){
                            Color(0xFF12140E).toArgb()
                        }else{
                            Color(0xFFF9FAEE).toArgb()
                        }
        setStatusBarColor(window, barColor)
        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            TheMovieDBAppTheme {
                NavigationWrapper()
            }
        }
    }

    @Suppress("DEPRECATION") // Safe to use in pre-VANILLA_ICE_CREAM versions
    private fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
                view.setBackgroundColor(color)

                // Adjust padding to avoid overlap
                view.setPadding(0, navigationBarInsets.top, 0, 0)
                insets
            }
        } else {
            // For Android 14 and below
            window.navigationBarColor = color
            window.statusBarColor = color
        }
    }

    override fun attachBaseContext(newBase: Context) {
        val context = runBlocking {
            val prefs = newBase.languagePreferencesDataStore.data.first()
            if (prefs.changeLanguage) {
                newBase.updateLocale(prefs.defaultLanguage)
            } else {
                newBase
            }
        }

        super.attachBaseContext(context)
    }
}



