package com.swayy.news.presentation

import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swayy.news.presentation.components.NewsItemCard
import com.swayy.news.presentation.components.SubNewsItemCard

@Composable
fun TrendingNewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    navigateNewsDetails: (String) -> Unit,
) {
    val state = newsViewModel.news.value

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.Text(
                text = "Trending News",
                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp, top = 16.dp, bottom = 16.dp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Column() {
                LazyColumn() {
                    items(state.news.take(1), key = {test -> test.title}) { news ->
                        NewsItemCard(imageUrl = news.imageUrl, title = news.title,navigateNewsDetails,news.url)
                    }
                    val subnews = state.news.drop(1).dropLast(1)

                    items(subnews, key = {data -> data.title}) { news ->
                        SubNewsItemCard(imageUrl = news.imageUrl, title = news.title,navigateNewsDetails,news.url)
                    }
                }

            }

        }

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }

    }

}