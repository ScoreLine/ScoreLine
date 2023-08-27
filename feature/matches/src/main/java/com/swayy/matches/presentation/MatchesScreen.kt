package com.swayy.matches.presentation

import android.content.Context
import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.runBlocking
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.swayy.matches.R
import com.swayy.matches.presentation.core.MatchComponent
import com.swayy.matches.presentation.core.MatchStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchesScreen(
    navigateMatchDetails: (Int, String) -> Unit,
    viewModel: MatchViewmodel = hiltViewModel()
) {

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .padding(top = 0.dp, start = 0.dp)
//                    .statusBarsPadding()
                    .background(androidx.compose.material3.MaterialTheme.colorScheme.primary),
            ) {

                Row(
                    modifier = Modifier.padding(top = 50.dp, start = 12.dp, end = 12.dp),
                ) {
                    androidx.compose.material3.Text(
                        text = "Matches",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.weight(2f))
                    androidx.compose.material.Icon(
                        painter = painterResource(id = com.swayy.core.R.drawable.baseline_download_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 0.dp),
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.primary

                    )
                }

            }

            Spacer(modifier = Modifier.height(0.dp))

            val tabRowItems = listOf(
                TabRowItem(
                    title = "Yesterday",
                    screen = {
                        MatchData(getFormattedYesterdayDate())
                    }
                ),
                TabRowItem(
                    title = "Today",
                    screen = {
                        MatchData(getFormattedTodayDate())
                    }
                ),
                TabRowItem(
                    title = "Tomorrow",
                    screen = {
                        MatchData(getFormattedTomorrowDate())
                    }

                ),


                )
            val pagerState = rememberPagerState(initialPage = 1)

            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                        )
                    },
                    backgroundColor = Color.LightGray.copy(alpha = .0F),
//                    edgePadding = 0.dp
                ) {
                    tabRowItems.forEachIndexed { index, item ->
                        Tab(
                            selected = pagerState.currentPage == index,
                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },

                            text = {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Spacer(Modifier.width(4.dp))
                                    Text(
                                        text = item.title,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 14.sp,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        )
                    }
                }
                HorizontalPager(
                    count = tabRowItems.size,
                    state = pagerState,
                    userScrollEnabled = false
                ) {
                    tabRowItems[pagerState.currentPage].screen()
                }
            }
        }

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            BannerAdView()
        }

    }


//    val coroutineScope = rememberCoroutineScope()
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(top = 56.dp)
//    ) {
//
//        Column(modifier = Modifier.fillMaxSize()) {
//            Header()
//            Spacer(modifier = Modifier.height(8.dp))
//            val tabRowItems = listOf(
//                TabRowItem(
//                    title = "Yesterday",
//                    screen = {
//                        TabScreen(
//                            viewModel,
//                            getFormattedDayBeforeYesterday(1),
//                            navigateMatchDetails,
//                            "data"
//                        )
//                    }
//                ),
//                TabRowItem(
//                    title = "Today",
//                    screen = {
//                        TabScreen(
//                            viewModel,
//                            LocalDate.now().toString(),
//                            navigateMatchDetails,
//                            ""
//                        )
//                    }
//                ),
//                TabRowItem(
//                    title = "Tomorrow",
//                    screen = {
//                        TabScreen(
//                            viewModel,
//                            getFormattedDayAfterTomorrow(1),
//                            navigateMatchDetails,
//                            "data"
//                        )
//                    }
//                ),
//
//
//                )
//
//            val pagerState = rememberPagerState(initialPage = 1)
//
//            Column(
//                modifier = Modifier
//                    .padding(0.dp)
//            ) {
//                ScrollableTabRow(
//                    selectedTabIndex = pagerState.currentPage,
//                    indicator = { tabPositions ->
//                        TabRowDefaults.Indicator(
//                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
//                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
//                        )
//                    },
//                    backgroundColor = Color.LightGray.copy(alpha = .0F),
//                ) {
//                    tabRowItems.forEachIndexed { index, item ->
//                        Tab(
//                            selected = pagerState.currentPage == index,
//                            onClick = { coroutineScope.launch { pagerState.animateScrollToPage(index) } },
//
//                            text = {
//                                Row(verticalAlignment = Alignment.CenterVertically) {
//                                    Spacer(Modifier.width(4.dp))
//                                    Text(
//                                        text = item.title,
//                                        maxLines = 1,
//                                        overflow = TextOverflow.Ellipsis,
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = 14.sp,
//                                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
//                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                    )
//                                }
//                            }
//                        )
//                    }
//                }
//                HorizontalPager(
//                    count = tabRowItems.size,
//                    state = pagerState,
//                    userScrollEnabled = false
//                ) {
//                    tabRowItems[pagerState.currentPage].screen()
//                }
//            }
//        }
//
//    }
}

//data class TabRowItem(
//    val title: String,
//    val screen: @Composable () -> Unit,
//)
//
//@RequiresApi(Build.VERSION_CODES.O)
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TabScreen(
//    viewModel: MatchViewmodel,
//    date: String,
//    navigateMatchDetails: (Int, String) -> Unit,
//    test:String
//) {
//    val matchState = viewModel.matches.value
//
//    Scaffold(
//
//    ) {
//        it
//        SwipeRefreshComponent(
//            isRefreshingState = matchState.isLoading,
//            onRefreshData = {
//                viewModel.getMatches(date = date)
//            }
//        ) {
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .background(androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface)
//            ) {
//
//
//
//                LazyColumn() {
//                    runBlocking {
//                        launch(Dispatchers.Default) {
//                            val leaguesWithFixtures = matchState.matches
//                                .filter { it.league.id in 1..120 }
//                                .sortedBy { it.league.id }
//                                .groupBy { it.league.name }
//
//                            leaguesWithFixtures.forEach { (leagueName, fixtures) ->
////                                item {
////                                    BannerAdView()
////                                }
//
//                                item (key = leagueName){
//                                    Card(
//                                        modifier = Modifier
//                                            .fillMaxWidth()
//                                            .padding(12.dp),
//                                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
//                                        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary)
//                                    ) {
//                                        Column {
//                                            var isArrowUp by remember { mutableStateOf(true) }
//
//                                            Row(modifier = Modifier.padding(start = 4.dp)) {
//                                                val logo =
//                                                    matchState.matches.filter { it.league.name == leagueName }
//
//                                                logo.take(1).forEach { it ->
//                                                    val image = it.league.logo
//
//                                                    //test
//                                                    val painter = rememberAsyncImagePainter(
//                                                        ImageRequest.Builder(LocalContext.current)
//                                                            .data(data = image)
//                                                            .apply(block = fun ImageRequest.Builder.() {
//                                                                crossfade(true).placeholder(R.drawable.placeholder)
//                                                            }).build()
//                                                    )
//                                                    Spacer(modifier = Modifier.width(4.dp))
//                                                    Image(
//                                                        painter = painter,
//                                                        contentDescription = "",
//                                                        modifier = Modifier
//                                                            .size(20.dp)
//                                                            .align(Alignment.CenterVertically)
//                                                        ,
//                                                        contentScale = ContentScale.Crop
//                                                    )
//                                                    Text(
//                                                        text = leagueName,
//                                                        modifier = Modifier
//                                                            .padding(10.dp)
//                                                            .align(Alignment.CenterVertically),
//                                                        fontWeight = FontWeight.Bold,
//                                                        fontSize = 16.sp,
//                                                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
//                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                    )
//                                                    Spacer(modifier = Modifier.weight(2f))
//                                                    IconButton(
//                                                        onClick = { isArrowUp = !isArrowUp },
//                                                    ) {
//                                                        Icon(
//                                                            painter = if (isArrowUp) painterResource(id = R.drawable.baseline_keyboard_arrow_up_24) else painterResource(
//                                                                id = R.drawable.baseline_keyboard_arrow_down_24
//                                                            ),
//                                                            contentDescription = null
//                                                        )
//                                                    }
//                                                }
//                                            }
//
//
//                                            Divider(thickness = 0.5.dp)
//                                            fixtures.forEach { fixture ->
//
//                                                AnimatedVisibility(
//                                                    visible = isArrowUp,
//                                                    enter = fadeIn() + expandVertically(),
//                                                    exit = fadeOut() + shrinkVertically()
//                                                ) {
//                                                    Column(modifier = Modifier.fillMaxWidth()) {
//                                                        Row(
//                                                            modifier = Modifier
//                                                                .align(Alignment.CenterHorizontally)
//                                                                .padding(
//                                                                    top = 10.dp,
//                                                                    bottom = 10.dp,
//                                                                    start = 10.dp,
//                                                                    end = 10.dp
//                                                                )
//                                                                .fillMaxWidth()
//                                                                .clickable(onClick = {
//                                                                    navigateMatchDetails(
//                                                                        fixture.fixture.id,
//                                                                        date
//                                                                    )
//                                                                }),
//                                                            horizontalArrangement = Arrangement.SpaceEvenly
//                                                        ) {
//                                                            if (fixture.fixture.status.long == "First Half") {
//                                                                MatchStatus(
//                                                                    text = fixture.fixture.status.elapsed.toString(),
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.primary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.long == "Second Half") {
//                                                                MatchStatus(
//                                                                    text = fixture.fixture.status.elapsed.toString(),
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.primary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "NS") {
//                                                                MatchStatus(
//                                                                    text = "NS",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.tertiary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "PST") {
//                                                                MatchStatus(
//                                                                    text = "PS",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "SUSP") {
//                                                                MatchStatus(
//                                                                    text = "SP",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "TBD") {
//                                                                MatchStatus(
//                                                                    text = "TBD",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "CANC") {
//                                                                MatchStatus(
//                                                                    text = "CANC",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.onPrimary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "FT") {
//                                                                MatchStatus(
//                                                                    text = "FT",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.primary
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "HT") {
//                                                                MatchStatus(
//                                                                    text = "HT",
//                                                                    androidx.compose.material3.MaterialTheme.colorScheme.primary
//                                                                )
//                                                            }
//
//                                                            Text(
//                                                                text = fixture.teams.home.name,
//                                                                modifier = Modifier
//                                                                    .padding(10.dp)
//                                                                    .width(56.dp)
//                                                                    .align(Alignment.CenterVertically),
//                                                                fontSize = 14.sp,
//                                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
//                                                                maxLines = 2
//                                                            )
//                                                            Spacer(modifier = Modifier.width(2.dp))
//                                                            val image = fixture.teams.home.logo
//
//                                                            //test
//                                                            val painter = rememberAsyncImagePainter(
//                                                                ImageRequest.Builder(LocalContext.current)
//                                                                    .data(data = image)
//                                                                    .apply(block = fun ImageRequest.Builder.() {
//                                                                        crossfade(true).placeholder(R.drawable.placeholder)
//                                                                    }).build()
//                                                            )
//                                                            Image(
//                                                                painter = painter,
//                                                                contentDescription = "",
//                                                                modifier = Modifier
//                                                                    .size(26.dp)
//                                                                    .align(Alignment.CenterVertically),
//                                                                contentScale = ContentScale.Crop
//                                                            )
//                                                            val imageone = fixture.teams.away.logo
//                                                            Spacer(modifier = Modifier.width(4.dp))
//
//
//                                                            if (fixture.fixture.status.short == "NS") {
//                                                                Text(
//                                                                    text = convertTimestampToTime(fixture.fixture.timestamp.toLong()),
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "HT") {
//                                                                val halfhome =
//                                                                    (fixture.score.halftime.home as? Double)?.toInt()
//                                                                        ?: 0
//                                                                val halfaway =
//                                                                    (fixture.score.halftime.away as? Double)?.toInt()
//                                                                        ?: 0
//                                                                Text(
//                                                                    text = halfhome.toString() + " - " + halfaway.toString(),
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//
//                                                            if (fixture.fixture.status.long == "First Half") {
//                                                                val halfhome = fixture.score.halftime.home
//                                                                val halfaway = fixture.score.halftime.away
//                                                                Text(
//                                                                    text = halfhome.toString() + " - " + halfaway.toString(),
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.long == "Second Half") {
//                                                                val halfhome = fixture.goals.home
//                                                                val halfaway = fixture.goals.away
//                                                                Text(
//                                                                    text = halfhome.toString() + " - " + halfaway.toString(),
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "CANC") {
//                                                                Text(
//                                                                    text = "CANC",
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "PST") {
//                                                                Text(
//                                                                    text = "PP",
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "SUSP") {
//                                                                Text(
//                                                                    text = "SUSP",
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//                                                            if (fixture.fixture.status.short == "TBD") {
//                                                                Text(
//                                                                    text = "TBD",
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                                )
//                                                            }
//
//                                                            val scorehome =
//                                                                (fixture.score.fulltime.home as? Double)?.toInt()
//                                                                    ?: 0
//                                                            val scoreaway =
//                                                                (fixture.score.fulltime.away as? Double)?.toInt()
//                                                                    ?: 0
//
//                                                            if (fixture.fixture.status.short == "FT") {
//                                                                Text(
//                                                                    text = scorehome.toString() + " - " + scoreaway,
//                                                                    modifier = Modifier
//                                                                        .padding(10.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    fontSize = 14.sp,
//                                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
//                                                                    fontWeight = FontWeight.Bold
//                                                                )
//                                                            }
//
//                                                            Spacer(modifier = Modifier.width(4.dp))
//                                                            //test
//                                                            val painterone = rememberAsyncImagePainter(
//                                                                ImageRequest.Builder(LocalContext.current)
//                                                                    .data(data = imageone)
//                                                                    .apply(block = fun ImageRequest.Builder.() {
//                                                                        crossfade(true).placeholder(R.drawable.placeholder)
//                                                                    }).build()
//                                                            )
//                                                            Image(
//                                                                painter = painterone,
//                                                                contentDescription = "",
//                                                                modifier = Modifier
//                                                                    .size(26.dp)
//                                                                    .align(Alignment.CenterVertically),
//                                                                contentScale = ContentScale.Crop
//                                                            )
//                                                            Spacer(modifier = Modifier.width(2.dp))
//                                                            Text(
//                                                                text = fixture.teams.away.name,
//                                                                modifier = Modifier
//                                                                    .padding(10.dp)
//                                                                    .width(60.dp)
//                                                                    .align(Alignment.CenterVertically),
//                                                                fontSize = 14.sp,
//                                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
//                                                                maxLines = 2
//                                                            )
//                                                        }
//
//                                                    }
//                                                }
//
//                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//                Row(modifier = Modifier.align(Alignment.BottomCenter)) {
//                    BannerAdView()
//                }
//
//                if (matchState.error.isNotBlank()) {
//                    Text(
//                        text = matchState.error,
//                        color = androidx.compose.material3.MaterialTheme.colorScheme.error,
//                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(horizontal = 20.dp)
//                            .align(Alignment.Center)
//                    )
//                }
//            }
//
//        }
//    }
//
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun getFormattedDayBeforeYesterday(sub: Long): String {
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
//    val dayBeforeYesterday = LocalDate.now().minusDays(sub)
//    return dayBeforeYesterday.format(formatter)
//}
//
//@RequiresApi(Build.VERSION_CODES.O)
//fun getFormattedDayAfterTomorrow(sub: Long): String {
//    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
//    val dayBeforeYesterday = LocalDate.now().plusDays(sub)
//    return dayBeforeYesterday.format(formatter)
//}
//
//@Composable
//fun Header() {
//    Row(
//        modifier = Modifier
//            .padding(start = 12.dp, end = 14.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//
//        androidx.compose.material3.Text(
//            text = "ScoreLine",
//            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
//            fontSize = 24.sp,
//            fontWeight = FontWeight.Bold,
//            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
//            modifier = Modifier.padding(start = 10.dp)
//        )
//
//        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_access_time_24),
//                contentDescription = null,
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
//                contentDescription = null,
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_search_24),
//                contentDescription = null,
//            )
//            Spacer(modifier = Modifier.width(20.dp))
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_more_vert_24),
//                contentDescription = null,
//            )
//        }
//
//    }
//
//}
//
//fun convertTimestampToTime(timestamp: Long): String {
//    val sdf = SimpleDateFormat("h:mm a", Locale.US)
//    val date = Date(timestamp * 1000) // Convert seconds to milliseconds
//    return sdf.format(date)
//}
//
@Composable
fun BannerAdView() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(AdSize.BANNER)

            // Check if the app is in debug mode
            val isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0

            // Set the appropriate adUnitId based on the mode
            val adUnitId = if (isDebuggable) {
                "ca-app-pub-3940256099942544/6300978111" // Test adUnitId
            } else {
                "ca-app-pub-3376169146760040/3025749162" // Official adUnitId
            }

            adView.adUnitId = adUnitId
            adView.loadAd(AdRequest.Builder().build())

            adView
        }
    )
}
//
//@Composable
//fun SwipeRefreshComponent(
//    isRefreshingState: Boolean,
//    onRefreshData: () -> Unit,
//    content: @Composable () -> Unit
//) {
//    SwipeRefresh(
//        state = rememberSwipeRefreshState(isRefreshing = isRefreshingState),
//        onRefresh = {
//            onRefreshData()
//        },
//        indicator = { state, refreshTrigger ->
//            SwipeRefreshIndicator(
//                state = state,
//                refreshTriggerDistance = refreshTrigger,
//                scale = true,
//                arrowEnabled = true,
//                refreshingOffset = 120.dp,
//                contentColor = androidx.compose.material3.MaterialTheme.colorScheme.primary
//            )
//        }
//    ) {
//        content()
//    }
//}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)


@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedTodayDate(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedYesterdayDate(): String {
    val yesterday = LocalDate.now().minusDays(1)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return yesterday.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedTomorrowDate(): String {
    val yesterday = LocalDate.now().plusDays(1)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return yesterday.format(formatter)
}

@Composable
fun MatchData(date: String) {
    val matchList = remember { mutableStateListOf<MatchItem>() }

    LaunchedEffect(Unit) {
        val url = "https://www.besoccer.com/livescore/$date"
        fetchMatches(url, matchList)

        // Launch fetchMatchDetails concurrently for all match items
//        matchList.forEach { matchItem ->
//            launch {
//                fetchMatchDetails(matchItem.matchLink, matchItem)
//            }
//        }
    }
    androidx.compose.material.Card(
        modifier = Modifier.padding(
            start = 12.dp,
            end = 12.dp,
            top = 4.dp
        ), elevation = 4.dp
    ) {
        LazyColumn {
            items(matchList) { match ->
                MatchItemCard(match)
                Divider(thickness = 0.5.dp, color = Color.LightGray)
            }

        }
    }

}


data class MatchItem(
    val league: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String,
    val awayTeamImageUrl: String,
    val matchTime: String,
    val round: String,
    val matchLink: String,
    var homeScore: String,
    var awayScore: String,
    var matchStatus: String = "",
    val matchProgress:String
)

@Composable
fun MatchItemCard(match: MatchItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 8.dp, end = 8.dp),
//        elevation = 4.dp
    ) {

        Column(
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp, top = 6.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = match.homeTeam,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(10.dp)
                        .width(64.dp)
                        .align(Alignment.CenterVertically)

                )
                Image(
                    painter = rememberImagePainter(match.homeTeamImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = match.matchProgress,
                        fontSize = 13.sp,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,

                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = match.homeScore,
                        fontSize = 16.sp,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Image(
                    painter = rememberImagePainter(match.awayTeamImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = match.awayTeam,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                    fontSize = 13.sp,
                    maxLines = 2,
                    modifier = Modifier
                        .padding(10.dp)
                        .width(64.dp)
                        .align(Alignment.CenterVertically)

                )
            }
            Spacer(modifier = Modifier.height(0.dp))

        }
    }
}


private suspend fun fetchMatches(url: String, matchList: MutableList<MatchItem>) {
    withContext(Dispatchers.IO) {
        try {
            val doc: Document = Jsoup.connect(url).get()
            val matchElements: Elements = doc.select(".match-link")
            val panelHeadElements = doc.select(".panel-head")

            for (matchElement in matchElements) {
                val homeTeam = matchElement.select(".team_left .name").text()

                if (homeTeam.isBlank()) {
                    continue
                }

                val awayTeam = matchElement.select(".team_right .name").text()
                val homeTeamImageUrl = matchElement.select("img[alt='${homeTeam}']").attr("src")
                val awayTeamImageUrl = matchElement.select("img[alt='${awayTeam}']").attr("src")
                val scoreElement = matchElement.select("div.marker").text()
                val scoreParts = scoreElement.split("-")
                val homeScore = scoreParts.getOrElse(0) { "0" }
                val awayScore = scoreParts.getOrElse(1) { "0" }
                val matchTime = matchElement.attr("starttime")
                val round = matchElement.select(".middle-info").text()
                val matchLink = matchElement.attr("href")

                val matchStatusElement = matchElement.select(".match-status-label .tag").text()
                val matchProgress = if (matchStatusElement.contains("'")) {
                    matchStatusElement
                } else {
                    matchStatusElement.replace("'", "").toLowerCase()
                }

                Log.e("home",scoreElement)
                Log.e("away",scoreElement)
                var league = ""

                // Find the correct league for the current match
                for (panelHeadElement in panelHeadElements) {
                    if (panelHeadElement.select("a[data-cy=competitionName] span.va-m")
                            .text() != ""
                    ) {
                        league =
                            panelHeadElement.select("a[data-cy=competitionName] span.va-m").text()
                        break
                    }
                }

                val matchItem = MatchItem(
                    league,
                    homeTeam,
                    awayTeam,
                    homeTeamImageUrl,
                    awayTeamImageUrl,
                    matchTime,
                    round,
                    scoreElement,
                    scoreElement,
                    matchLink,
                    matchProgress = matchProgress
                )

                matchList.add(matchItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}


//private suspend fun fetchMatchDetails(matchLink: String, matchItem: MatchItem) {
//    withContext(Dispatchers.IO) {
//        try {
//            val doc = Jsoup.connect(matchLink).get()
//
//            val scoreElement = doc.select("div.marker .data")
//            val scoreText = scoreElement.text()
//            val scores = scoreText.split(" - ")
//
//            val homeScore = scores.getOrNull(0)?.toIntOrNull() ?: 0
//            val awayScore = scores.getOrNull(1)?.toIntOrNull() ?: 0
//
//            val matchStatus = doc.select(".tag.end").text()
//
//            matchItem.homeScore = homeScore
//            matchItem.awayScore = awayScore
//            matchItem.matchStatus = matchStatus
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}
