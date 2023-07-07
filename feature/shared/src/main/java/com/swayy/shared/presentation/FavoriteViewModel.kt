package com.swayy.shared.presentation

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.core.util.UiEvents
import com.swayy.shared.domain.model.Favorite
import com.swayy.shared.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepository: FavoriteRepository,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

    private val _favoritesUiState = mutableStateOf(FavoritesUiState())
    val favoritesUiState = _favoritesUiState

    fun insertAFavorite(
        name: String,
        imageUrl: String,
        websiteUrl: String,
    ) {
        viewModelScope.launch {
            val favorite = Favorite(
                name = name,
                imageUrl = imageUrl,
                websiteUrl = websiteUrl,
                isFavorite = true
            )
            when (
                val result = favoritesRepository.insertFavorite(
                    favorite = favorite
                )
            ) {
                is Resource.Error -> {
                    _eventsFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "An error occurred"
                        )
                    )
                }

                is Resource.Success -> {
                    _eventsFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = "Added to favorites"
                        )
                    )
                }

                else -> {}
            }
        }
    }

    fun inOnlineFavorites(name: String): LiveData<Boolean> {
        return favoritesRepository.isOnlineFavorite(name = name)
    }

    fun deleteAnOnlineFavorite(name: String) {
        viewModelScope.launch {
            favoritesRepository.deleteAnOnlineFavorite(
                name = name
            )
        }
    }

    fun getFavorites() {
        _favoritesUiState.value =
            _favoritesUiState.value.copy(
                isLoading = true,
                favorites = emptyList(),
                error = null
            )
        viewModelScope.launch {
            when (val result = favoritesRepository.getFavorites()) {
                is Resource.Error -> {
                    _eventsFlow.emit(
                        UiEvents.SnackbarEvent(
                            message = result.message ?: "An unexpected error occurred"
                        )
                    )

                    result.data?.collectLatest { favorites ->
                        _favoritesUiState.value = _favoritesUiState.value.copy(
                            isLoading = false,
                            favorites = favorites,
                            error = result.message
                        )
                    }
                }
                is Resource.Success -> {
                    result.data?.collectLatest { favorites ->
                        _favoritesUiState.value = _favoritesUiState.value.copy(
                            isLoading = false,
                            favorites = favorites,
                            error = null
                        )
                    }
                }
                else -> {
                    favoritesUiState
                }
            }
        }
    }

    fun deleteAllFavorites() {
        viewModelScope.launch {
            favoritesRepository.deleteAllFavorites()
        }
    }


}

data class FavoritesUiState(
    val favorites: List<Favorite> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)