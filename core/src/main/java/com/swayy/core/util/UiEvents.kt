package com.swayy.core.util

sealed class UiEvents {
    data class SnackbarEvent(val message: String) : UiEvents()
}
