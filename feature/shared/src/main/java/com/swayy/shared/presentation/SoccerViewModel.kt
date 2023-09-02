package com.swayy.shared.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.shared.domain.use_case.GetSoccerUseCase
import com.swayy.shared.presentation.state.ClubsListState
import com.swayy.shared.presentation.state.SoccerListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SoccerViewModel @Inject constructor(
    private val getSoccerUseCase: GetSoccerUseCase
):ViewModel() {

    private val _soccer = mutableStateOf(SoccerListState())
    val soccer: State<SoccerListState> = _soccer

    private var isDataLoaded = false

    fun getSoccer(matchLink:String) {
        viewModelScope.launch {
            try {
                _soccer.value = SoccerListState(isLoading = true)

                val result = getSoccerUseCase.invoke(matchLink)
                result.collect { newsResult ->
                    when (newsResult) {
                        is Resource.Success -> {
                            _soccer.value = SoccerListState(soccer = newsResult.data ?: emptyList(), isLoading = false)
                            isDataLoaded = true
                        }

                        is Resource.Error -> {
                            _soccer.value = SoccerListState(
                                error = newsResult.message ?: "An unexpected error occurred"
                            )
                        }

                        is Resource.Loading -> {
                            // No need to update _matches for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _soccer.value = SoccerListState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }

}