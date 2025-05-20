package com.osmar.themoviedbapp.utils

import android.util.Log
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


}
enum class ImageSize(val size: String){
    SMALL("w92"),
    BIG("w300")
}
