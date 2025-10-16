package com.osmar.themoviedbapp.utils

import androidx.compose.ui.graphics.Color
import com.osmar.themoviedbapp.data.ApiConstants

object Utils {
    fun getUrlImage(size : String, endpoint : String?) : String{
        if (endpoint.isNullOrBlank()) {
            return ""
        }
        val url = "${ApiConstants.BASE_URL_PATH_IMAGE}${size}/${endpoint}"
//        Log.d("getUrlImage",url)
        return url
    }

    fun provideColor(isSelected : Boolean, activeColor : Color, passiveColor : Color) : Color {
        return if (isSelected) activeColor else passiveColor
    }
}
enum class ImageSize(val size: String){
//    SMALL("w92"),
//    SEMISMALL("w154"),
    BIG("w300")
}
