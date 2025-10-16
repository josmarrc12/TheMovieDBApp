package com.osmar.themoviedbapp.ui.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.osmar.themoviedbapp.data.datastore.LanguageManager
import com.osmar.themoviedbapp.datastore.LanguagePreferences.Languages
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuViewModel @Inject constructor(
    private val languageManager: LanguageManager
) : ViewModel(){

    private val _resetFlag = MutableStateFlow(0)
    val resetFlag = _resetFlag.asStateFlow()

    val languageState = languageManager.languageSelected
        .stateIn(viewModelScope, SharingStarted.Eagerly, Languages.ENGLISH)


    fun changeLanguage(language: Languages){
        viewModelScope.launch {
            languageManager.setLanguage(language)
            languageManager.setFlagIsLanguageChange()
            _resetFlag.emit(1)
        }
    }
}
