package com.swayy.news.presentation

import android.content.Context
import android.content.pm.ApplicationInfo
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.swayy.core.core.components.AdaptiveBanner
import com.swayy.core.core.components.LargeAdView
import com.swayy.news.presentation.components.NewsItemCard
import com.swayy.news.presentation.components.SubNewsItemCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

@Composable
fun TrendingNewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    navigateNewsDetails: (String) -> Unit,
) {

//    val match = remember { mutableStateOf<Match?>(null) }
//
//    // LaunchedEffect will start a coroutine when the App Composable is first composed
//    LaunchedEffect(key1 = true) {
//        val scrapedMatch = withContext(Dispatchers.IO) {
//            scrapeMatchDetails("https://www.besoccer.com/match/afc-bournemouth/tottenham-hotspur-fc/202412639")
//        }
//        match.value = scrapedMatch
//    }
//
//    Column {
//        // Other UI elements
//        match.value?.let { MatchList(listOf(it)) }
//    }

    val state = newsViewModel.news.value

    val context = LocalContext.current
    MobileAds.initialize(context) { }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            androidx.compose.material3.Text(
                text = "Trending News",
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 12.dp, top = 13.dp, bottom = 13.dp),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Column() {
                LazyColumn() {
                    items(state.news.take(1), key = {test -> test.title}) { news ->
                        NewsItemCard(imageUrl = news.imageUrl, title = news.title,navigateNewsDetails,news.url)
                    }

                    item {
                        Row() {
                            LargeAdView()
                        }
                    }
                    val subnews = state.news.drop(1).dropLast(1)

                    items(subnews, key = {data -> data.title}) { news ->
                        SubNewsItemCard(imageUrl = news.imageUrl, title = news.title,navigateNewsDetails,news.url)
                    }
                    item {
                        Row() {
                            LargeAdView()
                        }
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

//@Composable
//fun BannerAdView() {
//    AndroidView(
//        modifier = Modifier
//            .fillMaxWidth(),
//        factory = { context ->
//            val adView = AdView(context)
//            adView.setAdSize(AdSize.LARGE_BANNER)
//
//            // Check if the app is in debug mode
//            val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
//
//            // Set the appropriate adUnitId based on the mode
//
//            adView.adUnitId = "ca-app-pub-3376169146760040/5555105317"
//            adView.loadAd(AdRequest.Builder().build())
//
//            adView
//        }
//    )
//}


/////dummy

//data class Match(
//    val homeTeam: String,
//    val awayTeam: String,
//    val scoreHome: Int,
//    val scoreAway: Int
//)
//
//
//suspend fun scrapeMatchDetails(url: String): Match? {
//    return withContext(Dispatchers.IO) {
//        val doc = Jsoup.connect(url).get()
//
//        val homeTeam = doc.select("div.team.match-team.left p.name a").text()
//        val awayTeam = doc.select("div.team.match-team.right p.name a").text()
//
//        val scoreElement = doc.select("div.marker .data")
//        val scoreText = scoreElement.text()
//        val scores = scoreText.split(" - ")
//
//        val scoreHome = scores.getOrNull(0)?.toIntOrNull() ?: 0
//        val scoreAway = scores.getOrNull(1)?.toIntOrNull() ?: 0
//
//        Match(homeTeam, awayTeam, scoreHome, scoreAway)
//    }
//}
//
//
//@Composable
//fun MatchItem(match: Match) {
//    Column {
//        Text("Home: ${match.homeTeam}")
//        Text("Away: ${match.awayTeam}")
//        Text("Score: ${match.scoreHome} - ${match.scoreAway}")
//    }
//}
//
//@Composable
//fun MatchList(matches: List<Match>) {
//    LazyColumn {
//        items(matches) { match ->
//            MatchItem(match)
//        }
//    }
//}