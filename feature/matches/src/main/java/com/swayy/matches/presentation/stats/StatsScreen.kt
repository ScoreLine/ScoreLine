package com.swayy.matches.presentation.stats

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swayy.core_network.model.stats.Statistic


@Composable
fun FixtureStatsScreen(
    fixture:Int,
    home: Int,
    away: Int,
    viewModel: StatsViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true){
        viewModel.getStats(fixture = fixture)
    }

    val state = viewModel.stats.value
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.stats) { match ->
                if (match.team?.id == home) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.Start)) {
                            DisplayStats(statistics = match.statistics)
                            Text(text = "ststs")
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
    }
}

@Composable
fun DisplayStats(statistics: List<Statistic>) {
    Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
        for (statistic in statistics) {
            when (statistic.type) {
                "Shots on Goal" -> {
                    Text(text = "Shots on Goal: ${statistic.value}")
                }
                "Shots off Goal" -> {
                    Text(text = "Possession: ${statistic.value}")
                }
                "Total Shots" -> {
                    Text(text = "Goals: ${statistic.value}")
                }
                "Fouls" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Corner Kicks" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Offsides" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Ball Possession" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Yellow Cards" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Red Cards" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Goalkeeper Saves" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Total passes" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                "Passes %" -> {
                    Text(text = "Shots off target: ${statistic.value}")
                }
                // Add more cases for other types as needed
                else -> {
                    Text(text = "Unknown type: ${statistic.type}")
                }
            }
        }
    }
}


//@Composable
//        fun StatsComponent(stats: StatsDomainModel, text: String) {
//            Row(
//                modifier = Modifier.fillMaxWidth(),
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Column(
//                    modifier = Modifier
//                        .padding(start = 6.dp),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = text,
//                        style = MaterialTheme.typography.body1
//                    )
//                }
//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stats.statistics,
//                        style = MaterialTheme.typography.body1
//                    )
//                }
//                Column(
//                    modifier = Modifier.weight(1f),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        text = stats.statistics,
//                        style = MaterialTheme.typography.body1
//                    )
//                }
//            }
//        }





