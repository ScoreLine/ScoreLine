package com.swayy.favourites.presentation.league_details

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
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
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.palette.graphics.Palette
import coil.compose.LocalImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Precision
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kanyideveloper.core.components.LoadingStateComponent
import com.kanyideveloper.core.components.SwipeRefreshComponent
import com.swayy.core.R
import com.swayy.core.core.LeagueDetail
import com.swayy.core.core.MatchItemCard
import com.swayy.core.core.components.ErrorStateComponent
import com.swayy.core.core.fetchLeagueDetails
import com.swayy.core.domain.model.WebMatch
import com.swayy.core.leagueStanding.LeagueStandingScreen
import com.swayy.core.viewmodel.WebMatchViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LeagueDetailScreen(
    leagueName: String,
    games: String,
    teams: String,
    logo: String,
    flag: String,
    link: String,
    navigateBack: () -> Unit,
    navigateMatchDetails: (String) -> Unit
) {


    fun convertToOriginalString(encodedString: String): String {
        val originalString = encodedString.replace("_SLASH_", "/")
        return originalString
    }

    var testing by remember { mutableStateOf<String?>("") }

    val convertedLogo = convertToOriginalString(logo)
    val convertedFlag = convertToOriginalString(flag)
    val convertedLink = convertToOriginalString(link)

    testing = convertedFlag
    Log.d("testing", testing ?: "")

    val matchDeta = remember { mutableStateListOf<LeagueDetail>() }

    LaunchedEffect(Unit) {
        fetchLeagueDetails(convertedLink, matchDeta)
    }

    val context = LocalContext.current

//    val background = extractDominantColor(context = context, imageUrl = convertedLogo)
    var backgroundState by remember { mutableStateOf<Color?>(null) }

    matchDeta.forEach { info ->
        if (convertedLogo == info.leagueLogo) {
            backgroundState = extractDominantColor(context = context, imageUrl = convertedLogo)
            Log.e("flag", convertedFlag)

            LeagueData(
                backgroundState = backgroundState ?: MaterialTheme.colorScheme.primary,
                navigateBack = navigateBack,
                navigateMatchDetails = navigateMatchDetails,
                leagueName = leagueName,
                games = games,
                teams = teams,
                convertedLogo = convertedLogo,
                convertedFlag = testing ?: "",
                convertedLink = convertedLink,
            )

        } else {
            val convertedTestFlag = convertToOriginalString(info.leagueLogo)

            backgroundState = extractDominantColor(context = context, imageUrl = info.leagueLogo)
            LeagueData(
                backgroundState = backgroundState ?: MaterialTheme.colorScheme.primary,
                navigateBack = navigateBack,
                navigateMatchDetails = navigateMatchDetails,
                leagueName = leagueName,
                games = info.leagueGames,
                teams = teams,
                convertedLogo = convertedTestFlag,
                convertedFlag = convertedLogo,
                convertedLink = convertedLink,
            )
        }
    }

}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LeagueData(
    backgroundState: Color,
    navigateBack: () -> Unit,
    navigateMatchDetails: (String) -> Unit,
    leagueName: String,
    games: String,
    teams: String,
    convertedLogo: String,
    convertedFlag: String,
    convertedLink: String,
) {
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column() {
            Column(
                modifier = Modifier
                    .background(backgroundState ?: MaterialTheme.colorScheme.primary)
                    .fillMaxWidth()
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
                        text = leagueName,
                        modifier = Modifier
                            .padding(10.dp)
                            .align(Alignment.CenterVertically),
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.surface,
                        textAlign = TextAlign.Center
                    )

                }


                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            androidx.compose.material3.Text(
                                text = games,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center
                            )
                            androidx.compose.material3.Text(
                                text = if (teams == "test") "" else teams,
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                textAlign = TextAlign.Center
                            )
                        }

                        Image(
                            painter = rememberImagePainter(convertedLogo),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(12.dp)
                                .align(Alignment.CenterVertically),
                        )
                        Image(
                            painter = rememberImagePainter(convertedFlag),
                            contentDescription = null,
                            modifier = Modifier
                                .size(70.dp)
                                .padding(12.dp)
                                .align(Alignment.CenterVertically),
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                fun transformUrl(originalUrl: String): String {
                    val baseUrl = "https://www.besoccer.com"
                    val pathSegments = originalUrl.split("/")

                    if (pathSegments.size > 4) {
                        // Assuming the original URL structure has at least 4 segments
                        // e.g., https://www.besoccer.com/competition/premier_league
                        val competition = pathSegments[4]
                        return "$baseUrl/competition/table/$competition"
                    } else {
                        // Handle invalid URL structure here (e.g., not enough segments)
                        return originalUrl
                    }
                }

                val newLeague = transformUrl(originalUrl = convertedLink)

                val tabRowItems = listOf(
                    TabRowItem(
                        title = "Matches",
                        screen = {
                            LeagueMatchesScreen(convertedLink, navigateMatchDetails)
                        }
                    ),
                    TabRowItem(
                        title = "Table",
                        screen = {
                            LeagueStandingScreen(url = newLeague)
                        }
                    ),
                )
                val pagerState = rememberPagerState(initialPage = 0)

                Column(
                    modifier = Modifier
                        .padding(0.dp)
                        .background(MaterialTheme.colorScheme.surface)
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
                                onClick = {
                                    coroutineScope.launch {
                                        pagerState.animateScrollToPage(
                                            index
                                        )
                                    }
                                },

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
}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueMatchesScreen(
    link: String,
    navigateMatchDetails: (String) -> Unit,
    viewModel: WebMatchViewModel = hiltViewModel()
) {
    val url = link
    val matchState = viewModel.matches.value

    val context = LocalContext.current

    LaunchedEffect(key1 = url) {
        viewModel.getWebMatch(url = url)
    }

    Scaffold(

    ) {
        it
        SwipeRefreshComponent(
            isRefreshingState = matchState.isLoading,
            onRefreshData = {
                viewModel.getWebMatch(url = url)
            }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                Card(
                    modifier = Modifier.padding(
                        start = 12.dp,
                        end = 12.dp,
                        top = 4.dp
                    ), elevation = 4.dp
                ) {

                    LazyColumn {
                        items(matchState.matches.filter { it.link != "" }) { match ->
                            MatchItemCard(match, navigateMatchDetails, context)
                            Divider(thickness = 0.5.dp, color = Color.LightGray)
                        }
                    }
                }

                // Loading data
                if (matchState.isLoading) {
                    LoadingStateComponent()
                }

                // An Error has occurred
                if (!matchState.isLoading && matchState.error != null) {
                    ErrorStateComponent(errorMessage = matchState.error!!)
                }


            }
        }
    }
}

@Composable
fun extractDominantColor(context: Context, imageUrl: String): Color {
    val imageLoader = LocalImageLoader.current

    val request = remember(imageUrl) {
        ImageRequest.Builder(context)
            .data(imageUrl)
            .precision(Precision.EXACT)
            .build()
    }

    val imagePainter = rememberImagePainter(request, imageLoader = imageLoader)
    var dominantColor by remember(imagePainter) { mutableStateOf(Color.White) }

    LaunchedEffect(imagePainter) {
        val drawable = imageLoader.execute(request).drawable
        val bitmap = (drawable as? BitmapDrawable)?.bitmap

        if (bitmap != null) {
            val softwareBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)
            val color = calculateDominantColor(softwareBitmap)
            dominantColor = color
        }
    }

    return dominantColor
}

private suspend fun calculateDominantColor(bitmap: Bitmap): Color {
    return withContext(Dispatchers.Default) {
        val palette = Palette.Builder(bitmap).generate()
        val dominantSwatch = palette.dominantSwatch ?: palette.vibrantSwatch ?: palette.mutedSwatch
        dominantSwatch?.rgb?.let { Color(it) } ?: Color.White
    }
}

