package com.swayy.matches.presentation

import android.content.pm.ApplicationInfo
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MenuDefaults
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
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
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
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
import coil.compose.rememberImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.MobileAds
import com.kanyideveloper.core.components.LoadingStateComponent
import com.kanyideveloper.core.components.SwipeRefreshComponent
import com.swayy.core.core.LeagueDetail
import com.swayy.core.core.MatchItemCard
import com.swayy.core.core.components.AdaptiveBanner
import com.swayy.core.core.components.ErrorStateComponent
import com.swayy.core.core.components.LargeAdView
import com.swayy.core.core.components.addInterstitialCallbacks
import com.swayy.core.core.components.loadInterstitial
import com.swayy.core.core.fetchLeagueDetails
import com.swayy.core.util.getFormattedTodayDate
import com.swayy.core.util.getFormattedTomorrowDate
import com.swayy.core.util.getFormattedYesterdayDate
import com.swayy.core.viewmodel.WebMatchViewModel
import com.swayy.matches.R
import com.swayy.shared.presentation.FavoriteViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchesScreen(
    navigateMatchDetails: (String) -> Unit,
    navigateSoccerDetails: (String, String, String, String, String, String) -> Unit,
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
                    .background(androidx.compose.material3.MaterialTheme.colorScheme.primary),
            ) {

                Row(
                    modifier = Modifier.padding(
                        top = 60.dp,
                        start = 12.dp,
                        end = 12.dp,
                        bottom = 10.dp
                    ),
                ) {
                    androidx.compose.material3.Text(
                        text = "ScoreLine",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.weight(2f))


                }

            }

            Spacer(modifier = Modifier.height(0.dp))

            val tabRowItems = listOf(
                TabRowItem(
                    title = "Yesterday",
                    screen = {
                        MatchData(
                            getFormattedYesterdayDate(),
                            navigateMatchDetails,
                            navigateSoccerDetails
                        )
                    }
                ),
                TabRowItem(
                    title = "Today",
                    screen = {
                        MatchData(
                            getFormattedTodayDate(),
                            navigateMatchDetails,
                            navigateSoccerDetails
                        )
                    }
                ),
                TabRowItem(
                    title = "Tomorrow",
                    screen = {
                        MatchData(
                            getFormattedTomorrowDate(),
                            navigateMatchDetails,
                            navigateSoccerDetails
                        )
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
            AdaptiveBanner()
        }

    }

}


data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MatchData(
    date: String, navigateMatchDetails: (String) -> Unit,
    navigateSoccerDetails: (String, String, String, String, String, String) -> Unit,
    viewModel: WebMatchViewModel = hiltViewModel(),
    favoriteViewModel: FavoriteViewModel = hiltViewModel(),
) {
    val url = "https://www.besoccer.com/livescore/$date"
    val matchState = viewModel.matches.value
    val favoritesUiState = favoriteViewModel.favoritesUiState.value

    val context = LocalContext.current

    MobileAds.initialize(context) { }

    val favorites = favoritesUiState.favorites

    val favoriteMatches = matchState.matches.filter { match ->
        favorites.any { it.name == match.league }
    }

    LazyColumn() {
        items(favorites) { data ->
            val favoriteLeagueName = data.name
        }
    }

    LaunchedEffect(key1 = true, block = {
        viewModel.getWebMatch(url = url)
        favoriteViewModel.getFavorites()
    })

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
                    .background(androidx.compose.material3.MaterialTheme.colorScheme.inverseOnSurface)
            ) {
                LazyColumn {
                    val groupedMatches = matchState.matches.groupBy { it.league }
                    val nonFavoriteMatches = groupedMatches.filter { (leagueName, _) ->
                        !favoriteMatches.any { it.league == leagueName }
                    }
                    item {
//                        LargeAdView()
                    }

                    item {
                        if (!favoriteMatches.isEmpty()) {
                            Card(
                                modifier = Modifier
                                    .padding(
                                        start = 12.dp,
                                        end = 12.dp,
                                        top = 4.dp,
                                        bottom = 8.dp
                                    ),
                                elevation = 3.dp,
                                backgroundColor = MaterialTheme.colorScheme.surface
                            ) {
                                Column {
                                    LeagueHeader(
                                        "Favorites", "", "", true
                                   ,{ _, _, _, _, _, _ -> } )
                                    Divider(thickness = 0.5.dp, color = Color.LightGray)
                                    favoriteMatches.forEach { match ->
                                        MatchItemCard(match, navigateMatchDetails,context)
                                        Divider(thickness = 0.5.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }

                    }

                    nonFavoriteMatches.forEach { (leagueName, matchesInLeague) ->
                        item {
                            Card(
                                modifier = Modifier
                                    .padding(
                                        start = 12.dp,
                                        end = 12.dp,
                                        top = 4.dp,
                                        bottom = 8.dp
                                    ),
                                elevation = 3.dp,
                                backgroundColor = MaterialTheme.colorScheme.surface
                            ) {
                                Column {
                                    LeagueHeader(
                                        leagueName,
                                        leagueImage = matchesInLeague.firstOrNull()?.leagueImage
                                            ?: "",
                                        leagueLink = matchesInLeague.firstOrNull()?.leagueLink
                                            ?: "",
                                        false,
                                        navigateSoccerDetails
                                    )
                                    Divider(thickness = 0.5.dp, color = Color.LightGray)
                                    matchesInLeague.forEach { match ->
                                        MatchItemCard(match, navigateMatchDetails,context)
                                        Divider(thickness = 0.5.dp, color = Color.LightGray)
                                    }
                                }
                            }
                        }
                        item {
//                            LargeAdView()
                            Spacer(modifier = Modifier.height(2.dp))
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
fun LeagueHeader(
    leagueName: String,
    leagueImage: String,
    leagueLink: String,
    isFavorite: Boolean,
    navigateSoccerDetails: (String, String, String, String, String, String) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    fun convertToEncodedString(input: String): String {
        val encodedString = input.replace("/", "_SLASH_")
        return encodedString
    }

    val link = convertToEncodedString(leagueName)
    val image = convertToEncodedString(leagueImage)
    val beforeLink = convertToEncodedString(leagueLink)

    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            navigateSoccerDetails(
                link,
                "test",
                "test",
                image,
                "test",
                beforeLink
            )
        }) {
        Spacer(modifier = Modifier.width(16.dp))
        if (isFavorite) {
            Image(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )
        } else {
            Image(
                painter = rememberImagePainter(leagueImage),
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
                    .align(Alignment.CenterVertically)
            )
        }

        Text(
            text = leagueName,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                .padding(16.dp),
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.weight(2f))
        if (!isFavorite) {
            var isMenuVisible by remember { mutableStateOf(false) }

            val menuItems = listOf("Add to Favorites")

            val menuIcon: Painter = painterResource(id = R.drawable.baseline_more_vert_24)

            val matchDeta = remember { mutableStateListOf<LeagueDetail>() }

            LaunchedEffect(Unit) {
                fetchLeagueDetails(leagueLink, matchDeta)
            }

            var gamesTesting by remember {
                mutableStateOf("")
            }
            var logoTesting by remember {
                mutableStateOf("")
            }


            matchDeta.forEach { info ->
                 gamesTesting = info.leagueGames
                 logoTesting = info.leagueLogo
            }

                Column(
                    modifier = Modifier.clickable {
                        isMenuVisible = true
                    }
                ) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = menuIcon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        colorFilter = ColorFilter.tint(Color.Black)
                    )

                    androidx.compose.material3.DropdownMenu(
                        expanded = isMenuVisible,
                        onDismissRequest = { isMenuVisible = false },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        menuItems.forEach { item ->
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Data(text = item) },
                                onClick = {
                                    // Handle the menu item click here
                                    when (item) {
                                        "Add to Favorites" -> {
                                            viewModel.insertAFavorite(
                                                name = link,
                                                imageUrl = logoTesting,
                                                websiteUrl = leagueLink,
                                                games = gamesTesting,
                                                teams = "test",
                                                flag = leagueImage
                                            )
                                        }
                                    }
                                    isMenuVisible = false
                                },
                                leadingIcon = { LeadingIcon() },
                            )
                        }
                    }
                }

            Spacer(modifier = Modifier.width(18.dp))
        }

    }

}

@Composable
fun Data(text:String){
    Text(text = text)
}

@Composable
fun LeadingIcon(){
    Image(
        painter = painterResource(id = R.drawable.baseline_favorite_24),
        contentDescription = null,
        modifier = Modifier
            .size(20.dp),
    )
}