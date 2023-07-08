package com.swayy.matches.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.matches.domain.model.Match
import com.swayy.matches.domain.repository.MatchRepository
import com.swayy.matches.domain.use_case.GetMatchUseCase
import com.swayy.matches.presentation.state.MatchState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import javax.inject.Inject


@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class MatchViewmodel @Inject constructor(
    private val getMatchUseCase: GetMatchUseCase,
    private val repository: MatchRepository
) : ViewModel() {

    private val _matches = mutableStateOf(MatchState())
    val matches: State<MatchState> = _matches

    private var isDataLoaded = false

    init {
        getMatches(date = LocalDate.now().toString())
    }

    fun getMatches(date: String) {
        viewModelScope.launch {
            try {
                _matches.value = MatchState(isLoading = true)

                getMatchUseCase(date = date).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            _matches.value = matches.value.copy(
                                isLoading = false,
                                matches = result.data ?: emptyList()
                            )
                        }
                        is Resource.Error -> {
                            _matches.value = matches.value.copy(
                                isLoading = false,
                                error = result.message!!
                            )
                        }
                        else -> {
                            matches
                        }
                    }
                }
            } catch (e: Exception) {
                _matches.value = MatchState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}