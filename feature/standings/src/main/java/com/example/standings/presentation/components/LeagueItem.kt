package com.example.standings.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.standings.presentation.leagues.LeaguesViewModel

@Composable
fun LeagueItem(
    leaguesViewModel: LeaguesViewModel = hiltViewModel(),
    navigateLeagueDetails: (String) -> Unit,
) {
    val state = leaguesViewModel.leagues.value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.leagues.take(20)) { league ->
                Row (modifier = Modifier.fillMaxSize().padding(6.dp).clickable(onClick = {
                    navigateLeagueDetails(league.league.id.toString())
                })){
                    Image(
                        modifier = Modifier
                            .height(35.dp)
                            .width(45.dp)
                            .padding(start = 14.dp)
                            .align(Alignment.CenterVertically),
                        painter = rememberImagePainter(data = league.league.logo),
                        contentDescription = " league image",
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(22.dp))

                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Text(
                            text = league.country.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal,
                            maxLines = 1,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = league.league.name,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        )
                    }


                }
                Spacer(modifier = Modifier.height(1.dp))
                Divider(color = Color.Gray.copy(alpha = .3f), thickness = .5.dp)

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