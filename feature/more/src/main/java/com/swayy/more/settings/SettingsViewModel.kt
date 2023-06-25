package com.swayy.more.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.data.datastore.AppSettingsManager
import com.swayy.core.data.datastore.ThemeSettingsManager
import com.swayy.compose_ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel
@Inject constructor(
    private val settingsDataManager: AppSettingsManager,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    @Inject
    lateinit var appThemeDataStore: ThemeSettingsManager


    var darkModeDialog by mutableStateOf(false)


    val darkTheme by lazy {
        appThemeDataStore.darkTheme
    }

    fun updateDarkTheme(value: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setDarkTheme(value)
        }

    val dynamicColors by lazy {
        appThemeDataStore.dynamicColors
    }

    fun updateDynamicColors(enabled: Boolean) =
        viewModelScope.launch {
            appThemeDataStore.setDynamicColors(enabled)
        }

    val amoledBlack by lazy {
        appThemeDataStore.amoledBlack
    }

    fun updateAmoledBlack(enabled: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setAmoledBlack(enabled)
        }


    val fontSize = settingsDataManager.fontSize
    fun updateFontSize(value: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsDataManager.setFontSize(value)
        }
    }

    val currentTheme by lazy {
        appThemeDataStore.currentTheme
    }

    fun updateCurrentTheme(theme: AppTheme) {
        viewModelScope.launch(Dispatchers.IO) {
            appThemeDataStore.setCurrentTheme(theme)
        }
    }

}