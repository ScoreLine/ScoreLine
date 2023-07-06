package com.swayy.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.core.util.UiEvents
import com.swayy.shared.domain.model.Favorite
import com.swayy.shared.domain.repository.FavoriteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoritesRepository: FavoriteRepository,
) : ViewModel() {

    private val _eventsFlow = MutableSharedFlow<UiEvents>()
    val eventsFlow = _eventsFlow

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

}