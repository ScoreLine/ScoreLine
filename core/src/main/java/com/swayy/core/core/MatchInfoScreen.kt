package com.swayy.core.core

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.swayy.core.R
import com.swayy.core.core.components.ErrorStateComponent
import com.swayy.core.leagueStanding.LeagueStandingScreen
import com.swayy.core.viewmodel.WebMatchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchInfoScreen(
    matchLink: String,
    navigateBack: () -> Unit,
    viewModel: MatchInfoViewModel = hiltViewModel()
) {

    fun convertToOriginalString(encodedString: String): String {
        val originalString = encodedString.replace("_SLASH_", "/")
        return originalString
    }

    val my_url = convertToOriginalString(matchLink)

    val state = viewModel.matchinfo.value

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.getMatchInfo(matchLink = my_url)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column {
            Column(
                modifier = Modifier.background(MaterialTheme.colorScheme.primary)
            ) {
                Row(
                    modifier = Modifier.padding(
                        start = 10.dp,
                        end = 12.dp,
                        top = 40.dp,
                        bottom = 12.dp
                    )
                ) {

                    IconButton(onClick = navigateBack) {
                        Icon(
                            painter = painterResource(R.drawable.ic_round_arrow_back_24),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surface
                        )
                    }

                    Text(
                        text = "Match Details",
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.weight(2f))
                }

            }

            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .height(160.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    state.matchinfo.forEach { data ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp), horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(100.dp)
                            ) {
                                val image = data.homeLogo

                                //test
                                val painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(data = image)
                                        .apply(block = fun ImageRequest.Builder.() {
                                            crossfade(true).placeholder(R.drawable.placeholder)
                                        }).build()
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(68.dp)
                                        .align(Alignment.CenterHorizontally),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = data.homeTeam,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Column {

                                Text(
                                    text = data.score,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    fontSize = 30.sp,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )

                                Text(
                                    text = data.date,
                                    modifier = Modifier
                                        .padding(4.dp)
                                        .align(Alignment.CenterHorizontally),
                                    fontSize = 12.sp,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = data.time,
                                    modifier = Modifier
                                        .padding(2.dp)
                                        .width(105.dp)
                                        .align(Alignment.CenterHorizontally),
                                    fontSize = 12.sp,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                                    fontWeight = FontWeight.Normal,
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.width(100.dp)
                            ) {
                                val image = data.awayLogo

                                //test
                                val painter = rememberAsyncImagePainter(
                                    ImageRequest.Builder(LocalContext.current)
                                        .data(data = image)
                                        .apply(block = fun ImageRequest.Builder.() {
                                            crossfade(true).placeholder(R.drawable.placeholder)
                                        }).build()
                                )
                                Image(
                                    painter = painter,
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(68.dp)
                                        .align(Alignment.CenterHorizontally),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                Text(
                                    text = data.awayTeam,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .align(Alignment.CenterHorizontally),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                    color = androidx.compose.material3.MaterialTheme.colorScheme.surface,
                                    textAlign = TextAlign.Center

                                )
                            }
                        }
                    }
                }
            }


            val tabRowItems = listOf(
                TabRowItem(
                    title = "Events",
                    screen = {
                        PreviewScreen(my_url)
                    }
                ),
                TabRowItem(
                    title = "Lineup",
                    screen = {
                        LineupScreen(my_url)
                    }

                ),
                TabRowItem(
                    title = "Table",
                    screen = {
                        LeagueStandingScreen(url = my_url)
                    }
                ),
            )
            val pagerState = rememberPagerState(initialPage = 0)

            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    backgroundColor = Color.LightGray.copy(alpha = .0F),

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
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface
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

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        // An Error has occurred
        if (!state.isLoading && state.error != null) {
            ErrorStateComponent(errorMessage = state.error!!)
        }

    }

}

data class MatchEvent(
    val eventTime: String,
    val eventType: String,
    val player1: String,
    val player2: String? = null,
    val eventImage: String? = null,
    val player1Image: String?,
    val side: String
)

data class Clash(
    val home: String,
    val homeText: String,
    val draw: String,
    val drawText: String,
    val away: String,
    val awayText: String?,
)

@Composable
fun PreviewScreen(
    link: String,
    viewmodel: WebMatchViewModel = hiltViewModel()
) {

    val matchEve = remember { mutableStateListOf<MatchEvent>() }
    val matchClash = remember { mutableStateListOf<Clash>() }

    LaunchedEffect(Unit) {
        fetchMatchEvents(link, matchEve)
        fetchClash(link, matchClash)
        viewmodel.getWebMatch(url = link + "/preview")
    }

    val state = viewmodel.matches.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                androidx.compose.material.Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentHeight(),
                    elevation = 4.dp
                ) {

                Column(modifier = Modifier
                    .wrapContentHeight()
                    .padding(12.dp)) {
                    if (matchEve.isNotEmpty()){
                        Text(
                            text = "Match Events",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(12.dp)

                        )
                    }
                    matchEve.forEach { matchEvent->

                        if (matchEvent.side == "left") {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Column(modifier = Modifier.align(Alignment.Start)) {

                                    if (matchEvent.eventType == "Goal") {
                                        Row(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                top = 8.dp
                                            )
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            val painter = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.eventImage)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painter,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .align(Alignment.CenterVertically),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(4.dp))

                                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                                matchEvent.player1?.let {
                                                    Text(
                                                        text = it,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                                if (matchEvent.player2 != null) {
                                                    Text(
                                                        text = "Assist by " + (matchEvent.player2
                                                            ?: ""),
                                                        fontSize = 12.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }

                                        }
                                    }
                                    if (matchEvent.eventType == "Substitution") {
                                        Row(modifier = Modifier.padding(start = 16.dp, top = 14.dp)) {
                                            Text(
                                                text = matchEvent.toString(),
                                                fontWeight = FontWeight.Bold,
                                                modifier = Modifier.align(Alignment.CenterVertically),
                                                fontSize = 15.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                            )

                                            Spacer(modifier = Modifier.width(18.dp))

                                            Column {
                                                androidx.compose.material.Card(
                                                    shape = RoundedCornerShape(100.dp),
                                                    modifier = Modifier.padding(top = 4.dp)
                                                ) {
                                                    Column {
                                                        Row(
                                                            modifier = Modifier
                                                                .size(14.dp)
                                                                .background(MaterialTheme.colorScheme.primary)
                                                        ) {
                                                            Image(
                                                                imageVector = Icons.Default.ArrowForward,
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .padding(2.dp)
                                                                    .align(Alignment.CenterVertically),
                                                                contentDescription = "",
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                        }
                                                    }
                                                }

                                                Spacer(modifier = Modifier.height(1.dp))

                                                androidx.compose.material.Card(
                                                    shape = RoundedCornerShape(100.dp),
                                                    modifier = Modifier
                                                ) {
                                                    Column {
                                                        Row(
                                                            modifier = Modifier
                                                                .size(14.dp)
                                                                .background(Color.Red)
                                                        ) {
                                                            Image(
                                                                imageVector = Icons.Default.ArrowBack,
                                                                modifier = Modifier
                                                                    .fillMaxSize()
                                                                    .padding(2.dp)
                                                                    .align(Alignment.CenterVertically),
                                                                contentDescription = "",
                                                                colorFilter = ColorFilter.tint(
                                                                    Color.White
                                                                )
                                                            )
                                                        }
                                                    }
                                                }

                                            }
                                            Spacer(modifier = Modifier.width(20.dp))
                                            Column {
                                                Spacer(modifier = Modifier.height(3.dp))
                                                matchEvent.player1?.let {
                                                    Text(
                                                        text = it,
                                                        fontSize = 14.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                                    )
                                                }
                                                Spacer(modifier = Modifier.height(1.dp))

                                                matchEvent.player2?.let {
                                                    Text(
                                                        text = it,
                                                        fontSize = 14.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.error
                                                    )
                                                }
                                            }
                                        }
                                    }

                                    if (matchEvent.eventType == "Yellow card") {
                                        Row(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                top = 14.dp,
                                                bottom = 8.dp
                                            )
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            androidx.compose.material.Card(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .height(16.dp)
                                                    .width(12.dp)
                                                    .background(
                                                        Color.Yellow
                                                    )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color.Yellow)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(20.dp))

                                            matchEvent.player1?.let {
                                                Text(
                                                    text = it,
                                                    fontSize = 14.sp,
                                                    modifier = Modifier.align(Alignment.CenterVertically),
                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }

                                    if(matchEvent.eventType == "2nd Yellow/Red"){
                                        Row(
                                            modifier = Modifier.padding(
                                                start = 16.dp,
                                                top = 12.dp,
                                                bottom = 8.dp
                                            )
                                        ) {
                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            androidx.compose.material.Card(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .height(16.dp)
                                                    .width(12.dp)
                                                    .background(
                                                        Color.Yellow
                                                    )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(MaterialTheme.colorScheme.error)
                                                )
                                            }

                                            Spacer(modifier = Modifier.width(20.dp))

                                            matchEvent.player1?.let {
                                                Text(
                                                    text = it,
                                                    fontSize = 14.sp,
                                                    modifier = Modifier.align(Alignment.CenterVertically),
                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                        }
                                    }


                                }
                            }
                        } else {
                            Column(modifier = Modifier.fillMaxSize()) {
                                Column(modifier = Modifier.align(Alignment.End)) {

                                    if (matchEvent.eventType == "Goal") {
                                        Row(
                                            modifier = Modifier.padding(
                                                end = 16.dp,
                                                top = 14.dp
                                            )
                                        ) {

                                            Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                                matchEvent.player1?.let {
                                                    Text(
                                                        text = it,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                                if (matchEvent.player2 != null) {
                                                    Text(
                                                        text = "Assist by " + (matchEvent.player2
                                                            ?: ""),
                                                        fontSize = 12.sp,
                                                        style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                    )
                                                }
                                            }

                                            Spacer(modifier = Modifier.width(4.dp))
                                            val painter = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.eventImage)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painter,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(38.dp)
                                                    .align(Alignment.CenterVertically),
                                                contentScale = ContentScale.Crop
                                            )
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }


                                        }
                                    }
//                                        if (match.type == "subst") {
//                                            Row(modifier = Modifier.padding(end = 16.dp, top = 14.dp)) {
//
//                                                Column {
//                                                    Spacer(modifier = Modifier.height(3.dp))
//                                                    match.assist?.let {
//                                                        Text(
//                                                            text = it.name,
//                                                            fontSize = 14.sp,
//                                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary
//                                                        )
//                                                    }
//                                                    Spacer(modifier = Modifier.height(1.dp))
//
//                                                    match.player?.let {
//                                                        Text(
//                                                            text = it.name,
//                                                            fontSize = 14.sp,
//                                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                            color = androidx.compose.material3.MaterialTheme.colorScheme.error
//                                                        )
//                                                    }
//                                                }
//
//                                                Spacer(modifier = Modifier.width(20.dp))
//
//
//                                                Column {
//                                                    androidx.compose.material.Card(
//                                                        shape = RoundedCornerShape(100.dp),
//                                                        modifier = Modifier.padding(top = 4.dp)
//                                                    ) {
//                                                        Column {
//                                                            Row(
//                                                                modifier = Modifier
//                                                                    .size(14.dp)
//                                                                    .background(MaterialTheme.colorScheme.primary)
//                                                            ) {
//                                                                Image(
//                                                                    imageVector = Icons.Default.ArrowForward,
//                                                                    modifier = Modifier
//                                                                        .fillMaxSize()
//                                                                        .padding(2.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    contentDescription = "",
//                                                                    colorFilter = ColorFilter.tint(
//                                                                        Color.White
//                                                                    )
//                                                                )
//                                                            }
//                                                        }
//                                                    }
//
//                                                    Spacer(modifier = Modifier.height(1.dp))
//
//                                                    androidx.compose.material.Card(
//                                                        shape = RoundedCornerShape(100.dp),
//                                                        modifier = Modifier
//                                                    ) {
//                                                        Column {
//                                                            Row(
//                                                                modifier = Modifier
//                                                                    .size(14.dp)
//                                                                    .background(Color.Red)
//                                                            ) {
//                                                                Image(
//                                                                    imageVector = Icons.Default.ArrowBack,
//                                                                    modifier = Modifier
//                                                                        .fillMaxSize()
//                                                                        .padding(2.dp)
//                                                                        .align(Alignment.CenterVertically),
//                                                                    contentDescription = "",
//                                                                    colorFilter = ColorFilter.tint(
//                                                                        Color.White
//                                                                    )
//                                                                )
//                                                            }
//                                                        }
//                                                    }
//
//                                                }
//                                                Spacer(modifier = Modifier.width(18.dp))
//
//                                                Text(
//                                                    text = match.time?.elapsed.toString(),
//                                                    fontWeight = FontWeight.Bold,
//                                                    modifier = Modifier.align(Alignment.CenterVertically),
//                                                    fontSize = 15.sp,
//                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
//                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
//                                                )
//
//                                            }
//                                        }


                                    if (matchEvent.eventType == "Yellow card") {
                                        Row(
                                            modifier = Modifier.padding(
                                                end = 16.dp,
                                                top = 14.dp,
                                                bottom = 8.dp
                                            )
                                        ) {
                                            matchEvent.player1?.let {
                                                Text(
                                                    text = it,
                                                    fontSize = 14.sp,
                                                    modifier = Modifier.align(Alignment.CenterVertically),
                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(20.dp))
                                            androidx.compose.material.Card(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .height(16.dp)
                                                    .width(12.dp)
                                                    .background(
                                                        Color.Yellow
                                                    )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(Color.Yellow)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))

                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }


                                        }
                                    }
                                    if(matchEvent.eventType =="2nd Yellow/Red"){
                                        Row(
                                            modifier = Modifier.padding(
                                                end = 16.dp,
                                                top = 14.dp,
                                                bottom = 8.dp
                                            )
                                        ) {
                                            matchEvent.player1?.let {
                                                Text(
                                                    text = it,
                                                    fontSize = 14.sp,
                                                    modifier = Modifier.align(Alignment.CenterVertically),
                                                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                    color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(20.dp))
                                            androidx.compose.material.Card(
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                                    .height(16.dp)
                                                    .width(12.dp)
                                                    .background(
                                                        Color.Yellow
                                                    )
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .background(MaterialTheme.colorScheme.error)
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(10.dp))
                                            val painterr = rememberAsyncImagePainter(
                                                ImageRequest.Builder(LocalContext.current)
                                                    .data(data = matchEvent.player1Image)
                                                    .apply(block = fun ImageRequest.Builder.() {
                                                        crossfade(true).placeholder(R.drawable.placeholder)
                                                    }).build()
                                            )
                                            Image(
                                                painter = painterr,
                                                contentDescription = "",
                                                modifier = Modifier
                                                    .size(40.dp)
                                                    .align(Alignment.CenterVertically)
                                                    .padding(2.dp),
                                                contentScale = ContentScale.Crop
                                            )

                                            Spacer(modifier = Modifier.width(10.dp))
                                            Card(
                                                modifier = Modifier
                                                    .size(34.dp)
                                                    .background(MaterialTheme.colorScheme.surface)
                                                    .clip(RoundedCornerShape(20.dp))
                                            ) {
                                                Box(
                                                    modifier = Modifier
                                                        .background(MaterialTheme.colorScheme.primary)
                                                        .fillMaxSize()
                                                ) {
                                                    Text(
                                                        text = matchEvent.eventTime,
                                                        maxLines = 1,
                                                        overflow = TextOverflow.Ellipsis,
                                                        fontWeight = FontWeight.Bold,
                                                        fontSize = 14.sp,
                                                        style = MaterialTheme.typography.bodyLarge,
                                                        color = MaterialTheme.colorScheme.surface,
                                                        modifier = Modifier.align(Alignment.Center)
                                                    )
                                                }

                                            }

                                        }
                                    }


                                }
                            }
                        }
                    }
                }

//                    LazyColumn(modifier = Modifier.wrapContentHeight()) {
//                        items(matchEve) { matchEvent ->
//
////                            EventItem(matchEvent = matchEvent)
////                            Divider(thickness = 0.5.dp, color = Color.LightGray)
//                        }
//                    }
                }
            }

            item {
                androidx.compose.material.Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .wrapContentHeight(),
                    elevation = 4.dp
                ) {
                    matchClash.forEach {
                        Column {
                            Text(
                                text = "Last Meetings",
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(12.dp)

                            )
                            Spacer(modifier = Modifier.height(0.dp))

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Column {
                                    Text(
                                        text = extractFirstValue(it.home).toString(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)

                                    )
                                    Text(
                                        text = extractFirstTwoWords(it.homeText).toString(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center

                                    )
                                }
                                Column {
                                    Text(
                                        text = it.draw,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)

                                    )
                                    Text(
                                        text = it.drawText,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center

                                    )
                                }
                                Column {
                                    Text(
                                        text = it.away,
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 22.sp,
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier.align(Alignment.CenterHorizontally)

                                    )
                                    Text(
                                        text = it.awayText.toString(),
                                        maxLines = 1,
                                        overflow = TextOverflow.Ellipsis,
                                        fontWeight = FontWeight.Normal,
                                        fontSize = 13.sp,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        textAlign = TextAlign.Center

                                    )
                                }
                            }
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                            val context = LocalContext.current
                            state.matches.filter { it.link != "" }.forEach { match ->
                                MatchItemCard(match, {}, context)
                                Divider(thickness = 0.5.dp, color = Color.LightGray)
                            }
                        }

                    }
                }

            }

            item {
                Spacer(modifier = Modifier.height(20.dp))
            }

        }


    }
}

fun extractFirstValue(input: String): Int? {
    val values = input.split(" ")
    if (values.isNotEmpty()) {
        try {
            return values[0].toInt()
        } catch (e: NumberFormatException) {
            // Handle the case where the first value is not a valid integer
            return null
        }
    }
    return null // Handle the case where the input string is empty
}

fun extractFirstTwoWords(input: String): String? {
    val words = input.split(" ")
    if (words.size >= 2) {
        return "${words[0]} ${words[1]}"
    }
    return null // Handle the case where there are not enough words in the input string
}

@Composable
fun StatScreen(link: String) {

}

data class Stat(
    val statName: String,
    val teamA: String,
    val teamB: String
)

@Composable
fun LineupScreen(link: String) {
    val matchLine = remember { mutableStateListOf<Lineup>() }

    val teamA = matchLine.dropLast(11)
    val teamB = matchLine.drop(11)

    LaunchedEffect(Unit) {
        fetchMatchLineup(link, matchLine)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        androidx.compose.material.Card(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxSize(),
            elevation = 4.dp
        ) {

            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(2.dp)
            ) {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    content = {
                        items(teamA.size) { index ->
                            LineupItem(lineup = teamA[index])
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                )
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    content = {
                        items(teamB.size) { index ->
                            LineupItem(lineup = teamB[index])
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                )
            }

        }

    }
}

@Composable
fun LineupItem(lineup: Lineup) {

    Row(modifier = Modifier.padding(15.dp)) {
        Spacer(modifier = Modifier.width(2.dp))
        Text(
            text = lineup.number,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        Spacer(modifier = Modifier.width(8.dp))

        val painterr = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = lineup.image)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true).placeholder(R.drawable.placeholder)
                }).build()
        )
        Image(
            painter = painterr,
            contentDescription = "",
            modifier = Modifier
                .size(40.dp)
                .align(Alignment.CenterVertically),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = lineup.name,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier
                .width(100.dp)
                .align(Alignment.CenterVertically),
            textAlign = TextAlign.Center

        )
    }
}

data class Lineup(
    val name: String,
    val url: String,
    val image: String,
    val number: String,
    val points: String
)

@Composable
fun EventItem(matchEvent: MatchEvent) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Card(
                modifier = Modifier
                    .size(34.dp)
                    .background(MaterialTheme.colorScheme.surface)
                    .clip(RoundedCornerShape(20.dp))
            ) {
                Box(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .fillMaxSize()
                ) {
                    Text(
                        text = matchEvent.eventTime,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

            }
            Spacer(modifier = Modifier.width(16.dp))
            val painter = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = matchEvent.eventImage)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true).placeholder(R.drawable.placeholder)
                    }).build()
            )
            Image(
                painter = painter,
                contentDescription = "",
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                Text(
                    text = matchEvent.eventType,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.width(100.dp),
                    textAlign = TextAlign.Center

                )
                matchEvent.player2?.let {
                    Text(
                        text = it,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.width(100.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            val painterr = rememberAsyncImagePainter(
                ImageRequest.Builder(LocalContext.current)
                    .data(data = matchEvent.player1Image)
                    .apply(block = fun ImageRequest.Builder.() {
                        crossfade(true).placeholder(R.drawable.placeholder)
                    }).build()
            )
            Image(
                painter = painterr,
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterVertically),
                contentScale = ContentScale.Crop
            )

        }
    }

}


private suspend fun fetchMatchEvents(
    matchLink: String,
    matchEventList: MutableList<MatchEvent>
) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink + "/events").get()

            val eventElements = doc.select(".table-played-match.all-events")

            for (eventElement in eventElements) {

                val side = determineSide(eventElement.select(".col-side img"))

                val eventTime = eventElement.select(".col-mid-rows .min").text()
                val eventTypeElement = eventElement.select(".event-wrapper img").firstOrNull()
                val eventType = eventTypeElement?.attr("alt")
                val player1 = eventElement.select(".col-name a.name").firstOrNull()?.text() ?: ""
                val player2 =
                    eventElement.select(".col-name a.name.color-grey2").firstOrNull()?.text()
                val eventImage = eventTypeElement?.attr("src")
                val player1Image = eventElement.select(".col-name img").firstOrNull()?.attr("src")


                if (eventImage != null && eventImage.isNotEmpty()) {
                    Log.e("event type",eventType!!)
                    val matchEvent = MatchEvent(
                        eventTime,
                        eventType ?: "",
                        player1,
                        player2,
                        eventImage,
                        player1Image,
                        side
                    )
                    matchEventList.add(matchEvent)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private fun determineSide(imgElements: Elements): String {
    for (imgElement in imgElements) {
        if (imgElement.hasClass("left-img")) {
            return "left"
        } else if (imgElement.hasClass("right-img")) {
            return "right"
        }
    }
    return "unknown"
}


private suspend fun fetchMatchLineup(
    matchLink: String,
    matchLineupList: MutableList<Lineup>
) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink + "/lineups").get()

            val playerElements = doc.select(".player-wrapper")

            for (playerElement in playerElements) {
                val playerJson =
                    playerElement.select("script[type=application/ld+json]").firstOrNull()?.data()
                val playerData = JSONObject(playerJson)

                val playerName = playerData.getString("name")
                val playerUrl = playerData.getString("url")
                val playerImage = playerData.getString("image")

                val playerNumber = playerElement.select(".name.num-lineups span.bold").text()
                val playerPoints = playerElement.select(".match-points").text()

                val lineup = Lineup(playerName, playerUrl, playerImage, playerNumber, playerPoints)
                matchLineupList.add(lineup)
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

//data class LeagueDetail(
//    val leagueGames: String,
//    val leagueLogo: String
//)
//
//private suspend fun fetchLeagueDetails(
//    matchLink: String,
//    matchDetailList: MutableList<LeagueDetail>
//) {
//    withContext(Dispatchers.IO) {
//        try {
//            val doc = Jsoup.connect(matchLink).get()
//
//            val leagueGames = doc.select("div.team-text.ta-r p").text()
//            // Fetch league logo
//            val leagueLogo = doc.select("div.img-container div.img-wrapper img").attr("src")
//
//
//            val matchDetail = LeagueDetail(
//                leagueGames, leagueLogo
//            )
//            matchDetailList.add(matchDetail)
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }
//}
data class LeagueDetail(
    val leagueGames: String,
    val leagueLogo: String
)

suspend fun fetchLeagueDetails(
    matchLink: String,
    matchDetailList: MutableList<LeagueDetail>
) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink).get()

            val leagueGames = doc.select("div.team-text.ta-r p").text()
            // Fetch league logo
            val leagueLogo = doc.select("div.img-container div.img-wrapper img").attr("src")


            val matchDetail = LeagueDetail(
                leagueGames, leagueLogo
            )
            matchDetailList.add(matchDetail)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

private suspend fun fetchClash(
    matchLink: String,
    matchClashList: MutableList<Clash>
) {
    withContext(Dispatchers.IO) {
        try {

            val doc = Jsoup.connect(matchLink + "/preview").get()

            val row =
                doc.select("div.panel.view-more.match-last-matches div.row.jc-sa.ta-c.pv20.ph5.br-bottom")

            // Extract the values from the row
            val homeValue = row.select("div:nth-child(1) p.big-num").text()
            val homeText = row.select("div:nth-child(1) p.color-grey2").text()

            val drawValue = row.select("div:nth-child(2) p.big-num").text()
            val drawText = row.select("div:nth-child(2) p.color-grey2").text()

            val awayValue = row.select("div:nth-child(3) p.big-num").text()
            val awayText = row.select("div:nth-child(3) p.color-grey2").text()

            // Create a Clash object and add it to the list
            val clash = Clash(homeValue, homeText, drawValue, drawText, awayValue, awayText)
            matchClashList.add(clash)


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}