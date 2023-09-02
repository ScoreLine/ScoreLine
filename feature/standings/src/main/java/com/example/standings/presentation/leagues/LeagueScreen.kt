package com.example.standings.presentation.leagues

import android.content.pm.ApplicationInfo
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import com.example.standings.domain.model.LeaguesDomainModel
import com.example.standings.presentation.components.LeagueItem
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.swayy.core.R
import com.swayy.core.core.components.AdaptiveBanner
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LeagueScreen(
    leaguesViewModel: LeaguesViewModel = hiltViewModel(),
    navigateLeagueDetails: (String) -> Unit,
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
                        text = "Leagues",
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
                    title = "Premier League",
                    screen = {
//                        LeagueItem(leaguesViewModel,navigateLeagueDetails)
                        LeagueData("premier_league")
                    }
                ),
                TabRowItem(
                    title = "Laliga",
                    screen = {
//                        LeagueItem(leaguesViewModel,navigateLeagueDetails)
                        LeagueData("primera_division")
                    }
                ),
                TabRowItem(
                    title = "Bundesliga",
                    screen = {
//                        LeagueItem(leaguesViewModel,navigateLeagueDetails)
                        LeagueData("bundesliga")
                    }
                ),
                TabRowItem(
                    title = "Serie A",
                    screen = {
//                        LeagueItem(leaguesViewModel,navigateLeagueDetails)
                        LeagueData("serie_a")
                    }
                ),
                TabRowItem(
                    title = "Ligue 1",
                    screen = {
//                        LeagueItem(leaguesViewModel,navigateLeagueDetails)
                        LeagueData("ligue_1")
                    }
                )
            )
            val pagerState = rememberPagerState(initialPage = 0)

            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                ScrollableTabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                            color = MaterialTheme.colorScheme.primary,
                        )
                    },
                    backgroundColor = Color.LightGray.copy(alpha = .0F),
                    edgePadding = 0.dp
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

        Row(modifier = Modifier.align(Alignment.BottomCenter)) {
            AdaptiveBanner()
        }
    }
}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)



@OptIn(ExperimentalPagerApi::class)
@Composable
fun LeagueData(league: String) {
    val coroutineScope = rememberCoroutineScope()

    val tabRowItems = listOf(
        TabRowItem(
            title = "Matches",
            screen = {
                DayMatch(league = league)
            }
        ),
        TabRowItem(
            title = "Table",
            screen = {
                MatchLoad(league)
            }
        )

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

@Composable
fun MatchLoad(league: String) {
    var standings by remember { mutableStateOf(emptyList<TeamStandings>()) }

    LaunchedEffect(true) {
        standings = scrapePremierLeagueStandings(league)
    }

    Column {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Table",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row {
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "PTS",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "MP",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "W",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "D",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "L",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.width(16.dp))
            }
        }

        LazyColumn {
            items(standings.take(20)) { team ->
                TeamStandingsItem(team)
                Divider(thickness = 0.5.dp, color = Color.LightGray)
            }
        }
    }

}

@Composable
fun DayMatch(league: String) {
    val soccerList = remember { mutableStateListOf<Soccer>() }

    LaunchedEffect(Unit) {
        val url = "https://www.besoccer.com/competitions"
        fetchSoccer(url, soccerList)

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
            items(soccerList) { match ->

                Text(
                    text = match.leagueName +" "+match.games+" "+match.teams,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                )

            }

        }
    }
}

data class TeamStandings(
    val position: Int,
    val logoUrl: String,
    val teamName: String,
    val points: Int,
    val matchesPlayed: Int,
    val wins: Int,
    val draws: Int,
    val losses: Int,
    val goalsFor: Int,
    val goalsAgainst: Int,
    val goalDifference: Int
)

@Composable
fun TeamStandingsItem(team: TeamStandings) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "${team.position}", fontWeight = FontWeight.Normal, fontSize = 16.sp,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Image(
            painter = rememberImagePainter(data = team.logoUrl),
            contentDescription = null,
            modifier = Modifier.size(30.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.width(130.dp),
            text = team.teamName,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
        )
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "${team.points}", fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${team.matchesPlayed}",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${team.wins}",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${team.draws}",
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Text(
                text = "${team.losses}", fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            )
            Spacer(modifier = Modifier.width(16.dp))
        }


    }
}

private suspend fun scrapePremierLeagueStandings(league: String): List<TeamStandings> {
    val standings = mutableListOf<TeamStandings>()
    val document: Document = withContext(Dispatchers.IO) {
        Jsoup.connect("https://www.besoccer.com/competition/table/$league").get()
    }

    val rows = document.select("tr.row-body")

    for (row in rows) {
        val positionText = row.select("td.number-box").text()
        val position = if (positionText.isNotEmpty()) positionText.toInt() else 0

        val logoUrl = row.select("td.td-shield img").attr("src")
        val teamName = row.select("td.name span.team-name").text()

        val pointsText = row.select("td.green.bold").text()
        val points = if (pointsText.isNotEmpty()) pointsText.toInt() else 0

        val winsAndDraws = row.select("td.name span.bg-match-res")
        val wins = winsAndDraws.count { it.hasClass("win") }
        val draws = winsAndDraws.count { it.hasClass("draw") }

        val lossesElement = winsAndDraws.firstOrNull()?.nextElementSibling()
        val lossesText = lossesElement?.text() ?: ""
        val losses = parseNumericValue(lossesText)

        val goalsForElement = lossesElement?.nextElementSibling()
        val goalsForText = goalsForElement?.text() ?: ""
        val goalsFor = parseNumericValue(goalsForText)

        val goalsAgainstElement = goalsForElement?.nextElementSibling()
        val goalsAgainstText = goalsAgainstElement?.text() ?: ""
        val goalsAgainst = parseNumericValue(goalsAgainstText)

        val goalDifferenceElement = goalsAgainstElement?.nextElementSibling()
        val goalDifferenceText = goalDifferenceElement?.text() ?: ""
        val goalDifference = parseNumericValue(goalDifferenceText)

        standings.add(
            TeamStandings(
                position,
                logoUrl,
                teamName,
                points,
                wins + draws + losses,
                wins,
                draws,
                losses,
                goalsFor,
                goalsAgainst,
                goalDifference
            )
        )
    }

    return standings
}

private fun parseNumericValue(text: String): Int {
    val numericPattern = Regex("\\d+")
    val match = numericPattern.find(text)
    return match?.value?.toInt() ?: 0
}


///duplicated code
data class MatchItem(
    val league: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeTeamImageUrl: String,
    val awayTeamImageUrl: String,
    val score: String,
    val matchTime: String,
    val round: String,
    val matchLink: String,
    var homeScore: Int = 0,  // New field for home score
    var awayScore: Int = 0,   // New field for away score
    var matchStatus: String = ""
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
                Text(
                    text = "${match.homeScore} - ${match.awayScore}",
                    fontSize = 16.sp,
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                )
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

private suspend fun fetchMatchDetails(matchLink: String, matchItem: MatchItem) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink).get()

            val scoreElement = doc.select("div.marker .data")
            val scoreText = scoreElement.text()
            val scores = scoreText.split(" - ")

            val homeScore = scores.getOrNull(0)?.toIntOrNull() ?: 0
            val awayScore = scores.getOrNull(1)?.toIntOrNull() ?: 0

            val matchStatus = doc.select(".tag.end").text()

            matchItem.homeScore = homeScore
            matchItem.awayScore = awayScore
            matchItem.matchStatus = matchStatus
        } catch (e: Exception) {
            e.printStackTrace()
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
                val score = matchElement.select(".match-hour .time").text()
                val matchTime = matchElement.attr("starttime")
                val round = matchElement.select(".middle-info").text()
                val matchLink = matchElement.attr("href")

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
                    score,
                    matchTime,
                    round,
                    matchLink
                )

                matchList.add(matchItem)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

data class Soccer(
    val leagueName:String,
    val games:String,
    val teams:String,
    val logo:String,
    val flag :String,
    val link:String
)

private suspend fun fetchSoccer(matchLink: String, soccerList: MutableList<Soccer>) {
    withContext(Dispatchers.IO) {
        try {
            val doc = Jsoup.connect(matchLink).get()

            val leagueItems = doc.select(".item-list li a.item-box")

            for (item in leagueItems) {
                val leagueName = item.select(".main-text").text()
                val games = item.select(".sub-text1 ").text()
                val teams = item.select(".sub-text2").text()
                val logo = item.select(".image-main img").attr("src")
                val flag = item.select(".flag-round").attr("src")
                val link = item.attr("href")

                val soccer = Soccer(leagueName, games, teams, logo, flag,link)
                soccerList.add(soccer)
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}








