package com.osmar.themoviedbapp.data.datastore


import androidx.datastore.core.DataStore
import com.osmar.themoviedbapp.datastore.LanguagePreferences
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LanguageManager @Inject constructor(
    private val languagePrefs: DataStore<LanguagePreferences>
){
    val languageSelected = languagePrefs.data
        .map { it.defaultLanguage }

    val isLanguageChange = languagePrefs.data
        .map { it.changeLanguage }

    suspend fun setLanguage(language : LanguagePreferences.Languages){
        languagePrefs.updateData { prefs ->
            prefs.toBuilder().setDefaultLanguage(language).build()
        }
    }

    suspend fun setFlagIsLanguageChange(){
        languagePrefs.updateData { prefs ->
            prefs.toBuilder().setChangeLanguage(true).build()
        }
    }
}