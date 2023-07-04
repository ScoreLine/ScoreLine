package com.swayy.shared.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.shared.domain.use_case.GetClubsUseCase
import com.swayy.shared.presentation.state.ClubsListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClubsViewModel @Inject constructor(
    private val getClubsUseCase: GetClubsUseCase
) : ViewModel() {

    private val _clubs = mutableStateOf(ClubsListState())
    val clubs: State<ClubsListState> = _clubs

    private var isDataLoaded = false

    init {
        getClubs()
    }


    fun getClubs() {
        viewModelScope.launch {
            try {
                _clubs.value = ClubsListState(isLoading = true)

                val result = getClubsUseCase.invoke()
                result.collect { newsResult ->
                    when (newsResult) {
                        is Resource.Success -> {
                            _clubs.value = ClubsListState(clubs = newsResult.data ?: emptyList(), isLoading = false)
                            isDataLoaded = true
                        }

                        is Resource.Error -> {
                            _clubs.value = ClubsListState(
                                error = newsResult.message ?: "An unexpected error occurred"
                            )
                        }

                        is Resource.Loading -> {
                            // No need to update _matches for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _clubs.value = ClubsListState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}