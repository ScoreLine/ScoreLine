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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.swayy.core.R
import com.swayy.core.leagueStanding.LeagueStandingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.jsoup.Jsoup

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchInfoScreen(
    matchLink: String,
    navigateBack: () -> Unit,
) {
    fun convertToOriginalString(encodedString: String): String {
        val originalString = encodedString.replace("_SLASH_", "/")
        return originalString
    }

    val my_url = convertToOriginalString(matchLink)

    val matchDeta = remember { mutableStateListOf<MatchDetail>() }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        fetchMatchDetails(my_url, matchDeta)
    }

    Log.e("ndo hii", my_url)

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

            Box(modifier = Modifier.background(MaterialTheme.colorScheme.primary).height(160.dp)) {
                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                ) {
                    matchDeta.forEach { data ->
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
    }

}

data class MatchDetail(
    val homeTeam: String,
    val awayTeam: String,
    val homeLogo: String,
    val awayLogo: String,
    val score: String,
    val date: String,
    val time: String
)

data class MatchEvent(
    val eventTime: String,
    val eventType: String,
    val player1: String,
    val player2: String? = null,
    val eventImage: String? = null,
    val player1Image: String?,
)

@Composable
fun PreviewScreen(link: String) {
    val matchEve = remember { mutableStateListOf<MatchEvent>() }

    LaunchedEffect(Unit) {
        fetchMatchEvents(link, matchEve)
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
            LazyColumn {
                items(matchEve) { matchEvent ->
                    EventItem(matchEvent = matchEvent)
                    Divider(thickness = 0.5.dp, color = Color.LightGray)
                }
            }
        }

    }
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
                    text = matchEvent.player1,
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


private suspend fun fetchMatchDetails(
    matchLink: String,
    matchDetailList: MutableList<MatchDetail>
) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink).get()

            val homeTeam = doc.select("div.team.match-team.left p.name a").text()
            val awayTeam = doc.select("div.team.match-team.right p.name a").text()
            val homeLogo = doc.select("div.circle-team.circle-local img").attr("src")
            val awayLogo = doc.select("div.circle-team.circle-visitor img").attr("src")
            val matchStatusElement = doc.select("div.marker .data")

            val dateElement = doc.select(".date.header-match-date").text()
            val timeElement = doc.select(".tag").text()

            val matchStatus = when {
                matchStatusElement.select("span.r1").isNotEmpty() -> {
                    val scoreHome = matchStatusElement.select("span.r1").text().toInt()
                    val scoreAway = matchStatusElement.select("span.r2").text().toInt()
                    "$scoreHome - $scoreAway"
                }

                matchStatusElement.select(".match_hour").isNotEmpty() -> {
                    matchStatusElement.select(".match_hour").text()
                }

                else -> ""
            }

            val matchDetail = MatchDetail(
                homeTeam,
                awayTeam,
                homeLogo,
                awayLogo,
                matchStatus,
                dateElement,
                timeElement
            )
            matchDetailList.add(matchDetail)


        } catch (e: Exception) {
            e.printStackTrace()
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
                val eventTime = eventElement.select(".col-mid-rows .min").text()
                val eventTypeElement = eventElement.select(".event-wrapper img").firstOrNull()
                val eventType = eventTypeElement?.attr("alt")
                val player1 = eventElement.select(".col-name a.name").firstOrNull()?.text() ?: ""
                val player2 =
                    eventElement.select(".col-name a.name.color-grey2").firstOrNull()?.text()
                val eventImage = eventTypeElement?.attr("src")
                val player1Image = eventElement.select(".col-name img").firstOrNull()?.attr("src")

                if (eventImage != null && eventImage.isNotEmpty()) {
                    val matchEvent = MatchEvent(
                        eventTime,
                        eventType ?: "",
                        player1,
                        player2,
                        eventImage,
                        player1Image
                    )
                    matchEventList.add(matchEvent)
                }
            }


        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
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