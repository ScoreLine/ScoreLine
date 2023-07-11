package com.swayy.matches.presentation.h2h

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.HeadToHeadDomainModel
import com.swayy.matches.domain.use_case.GetHeadToHeadUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadToHeadViewModel @Inject constructor(
    private val getHeadToHeadUseCase: GetHeadToHeadUseCase
): ViewModel() {
    private val _h2h = mutableStateOf(H2HState())
    val h2h: State<H2HState> = _h2h

    private var isDataLoaded = false

    fun getH2H(h2h: String) {
        viewModelScope.launch {
            try {
                _h2h.value = H2HState(isLoading = true)

                getHeadToHeadUseCase(h2h = h2h).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _h2h.value = H2HState(h2h = result.data ?: emptyList())
                            isDataLoaded = true
                        }
                        is Resource.Error -> {
                            _h2h.value = H2HState(
                                error = result.message ?: "An unexpected error occurred"
                            )
                        }
                        is Resource.Loading -> {
                            // No need to update _h2h for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _h2h.value = H2HState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}

data class H2HState(
    val isLoading: Boolean = false,
    val error: String = "",
    val h2h: List<HeadToHeadDomainModel> = emptyList()
)
