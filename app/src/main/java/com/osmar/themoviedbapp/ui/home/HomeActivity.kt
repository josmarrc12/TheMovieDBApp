package com.osmar.themoviedbapp.ui.home

import android.os.Build
import android.os.Bundle
import android.view.Window
import android.view.WindowInsets
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import com.osmar.themoviedbapp.ui.nav.NavigationWrapper
import com.osmar.themoviedbapp.ui.theme.TheMovieDBAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class   HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setStatusBarColor(window, Color(0xFF181924).toArgb())
        WindowCompat.setDecorFitsSystemWindows(window,false)
        setContent {
            TheMovieDBAppTheme {
               NavigationWrapper()
            }
        }
    }


    private fun setStatusBarColor(window: Window, color: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.VANILLA_ICE_CREAM) { // Android 15+
            window.decorView.setOnApplyWindowInsetsListener { view, insets ->
                val navigationBarInsets = insets.getInsets(WindowInsets.Type.navigationBars())
                val statusBarInsets = insets.getInsets(WindowInsets.Type.statusBars())
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

}



