package com.swayy.matches.presentation.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.swayy.matches.domain.model.StatsDomainModel


@Composable
fun FixtureStatsScreen(
    fixture: String,
    home: Int,
    away: Int,
) {
    val viewModel: StatsViewModel = hiltViewModel()

    LaunchedEffect(key1 = true){
        viewModel.getStats(fixture = fixture )
    }

    val state = viewModel.stats.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.stats) { match ->
                if (match.team?.id == home) {

                }
            }
        }
        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
        }
    }
}

/**
 * rough component for this ui
 */
//@Composable
//fun StatsComponent(stats: StatsDomainModel) {
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(start = 6.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stats.team.name,
//                style = MaterialTheme.typography.body1
//            )
//        }
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stats.statistics.typ,
//                style = MaterialTheme.typography.body1
//            )
//        }
//        Column(
//            modifier = Modifier.weight(1f),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Text(
//                text = stats.team.name,
//                style = MaterialTheme.typography.body1
//            )
//        }
//    }
//}




