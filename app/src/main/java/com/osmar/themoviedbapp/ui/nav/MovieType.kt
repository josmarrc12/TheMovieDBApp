package com.osmar.themoviedbapp.ui.nav

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.osmar.themoviedbapp.ui.home.models.MovieModel
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val movieType = object :NavType<MovieModel>(isNullableAllowed = false){
    override fun get(bundle: Bundle, key: String): MovieModel? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, MovieModel::class.java)
        }else{
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): MovieModel {
        return Json.decodeFromString<MovieModel>(value)
    }

    override fun serializeAsValue(value: MovieModel): String {
        return Uri.encode(Json.encodeToString(value))
    }

    override fun put(bundle: Bundle, key: String, value: MovieModel) {
        bundle.putParcelable(key, value)
    }
}