package com.example.standings.presentation.leagues

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.presentation.viewmodel.LeaguesViewModel

@Composable
fun LeagueScreen(
    viewModel: LeaguesViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = true){
        viewModel.getLeagues()
    }

    val state = viewModel.leagues.value
    Box(modifier = Modifier.fillMaxSize().padding(top = 44.dp)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.leagues) { data ->
                LeagueItem(
                    league = data,
                    onItemClick = {
                       // navController.navigate(Screen.CoinDetailScreen.route + "/${coin.id}")
                    }
                )
            }
        }
        if(state.error.isNotBlank()) {
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
        if(state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun LeagueItem(league: LeaguesDomainModel, onItemClick: () -> Unit) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
//            AsyncImage(
//                model = ImageRequest.Builder(LocalContext.current)
//                    .data(league.country.flag)
//                    .decoderFactory(SvgDecoder.Factory())
//                    .build(),
//                modifier = Modifier
//                    .width(100.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .height(120.dp),
//                contentDescription = null
//            )
            Image(
                modifier = Modifier
                    .width(100.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .height(120.dp),
                painter = rememberImagePainter(data = league.league.logo),
                contentDescription = " logo image",
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = league.country.name,
                    modifier = Modifier.padding(6.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 1,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = league.league.name,
                    modifier = Modifier.padding(6.dp),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Normal,
                    fontFamily = FontFamily.SansSerif,
                    maxLines = 1,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}



