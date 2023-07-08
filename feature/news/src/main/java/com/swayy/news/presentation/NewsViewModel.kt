package com.swayy.news.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.swayy.core.util.Resource
import com.swayy.news.domain.use_case.GetNewsUseCase
import com.swayy.news.presentation.state.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase
) : ViewModel() {

    private val _news = mutableStateOf(NewsState())
    val news: State<NewsState> = _news

    private var isDataLoaded = false

    init {
        getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            try {
                _news.value = NewsState(isLoading = true)

                val result = getNewsUseCase.invoke()
                result.collect { newsResult ->
                    when (newsResult) {
                        is Resource.Success -> {
                            _news.value = NewsState(news = newsResult.data ?: emptyList(), isLoading = false)
                            isDataLoaded = true
                        }

                        is Resource.Error -> {
                            _news.value = NewsState(
                                error = newsResult.message ?: "An unexpected error occurred"
                            )
                        }

                        is Resource.Loading -> {
                            // No need to update _matches for Loading state, as it's already set above
                        }
                    }
                }
            } catch (e: Exception) {
                _news.value = NewsState(
                    error = "An unexpected error occurred: ${e.message}"
                )
            }
        }
    }
}