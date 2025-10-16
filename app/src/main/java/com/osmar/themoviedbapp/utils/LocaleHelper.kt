package com.osmar.themoviedbapp.utils

import com.osmar.themoviedbapp.datastore.LanguagePreferences.Languages
import java.util.Locale

object LocaleHelper {
    fun getDeviceLanguage() : Languages{
        val locale = Locale.getDefault().language
        return when(locale){
            "es" -> Languages.SPANISH
            "en" -> Languages.ENGLISH
            else -> Languages.ENGLISH
        }
    }
}