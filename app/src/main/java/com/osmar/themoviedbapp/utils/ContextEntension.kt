package com.osmar.themoviedbapp.utils

import android.content.Context
import android.content.res.Configuration
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.osmar.themoviedbapp.data.datastore.LanguagePreferencesSerializer
import com.osmar.themoviedbapp.datastore.LanguagePreferences
import com.osmar.themoviedbapp.datastore.LanguagePreferences.Languages
import java.util.Locale

val Context.languagePreferencesDataStore: DataStore<LanguagePreferences> by dataStore(
    fileName = "language_prefs.pb",
    serializer = LanguagePreferencesSerializer
)

fun Context.updateLocale(language : Languages): Context {
    val locale = when(language){
        Languages.UNSPECIFIED -> {Locale("en")}
        Languages.SPANISH -> {Locale("es")}
        Languages.ENGLISH -> { Locale("en")}
        Languages.UNRECOGNIZED -> {Locale("en")}
    }
    val config = Configuration(resources.configuration)
    Locale.setDefault(locale)
    config.setLocale(locale)
    return createConfigurationContext(config)
}