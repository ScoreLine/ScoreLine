package com.example.standings.presentation.leagues

import androidx.compose.foundation.Image
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
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.example.standings.presentation.standings.viewmodel.StandingsViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.swayy.core.R
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun LeagueDetails(
    league: String,
    navigateBack: () -> Unit,
    viewModel: StandingsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true){
        viewModel.getStandings(league.toInt(), 2022)
    }

    val state = viewModel.standings.value

    Box(
        modifier = Modifier
            .padding(top = 40.dp)
            .fillMaxSize()
    ) {
        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        Column {
            Row(modifier = Modifier.padding(start = 10.dp, end = 12.dp)) {

                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(R.drawable.ic_round_arrow_back_24),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.weight(2f))


            }

            LazyColumn(){
                items(state.standings){standing->
                    Row (modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp)
                        .padding(6.dp)) {
                        Image(
                            modifier = Modifier
                                .height(60.dp)
                                .width(60.dp)
                                .padding(start = 14.dp)
                                .align(Alignment.CenterVertically),
                            painter = rememberImagePainter(data = standing.logo),
                            contentDescription = " league image",
                            contentScale = ContentScale.Crop
                        )

                        Spacer(modifier = Modifier.width(18.dp))

                        Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                            Text(
                                text = standing.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                maxLines = 1,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = standing.country,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Normal,
                                maxLines = 1,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                            )
                        }


                    }
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }


            val pagerState = rememberPagerState(initialPage = 0)
            val coroutineScope = rememberCoroutineScope()
            val context = LocalContext.current


            val tabRowItems = listOf(

                TabRowItem(
                    title = "Standings",
                    screen = {
                        StandingScreen()
                    }
                ),
                TabRowItem(
                    title = "Top Scorers",
                    screen = {
                        ScorerScreen()
                    }
                )
            )

            Spacer(modifier = Modifier.height(2.dp))
            Column(
                modifier = Modifier
                    .padding(0.dp)
            ) {
                TabRow(
                    selectedTabIndex = pagerState.currentPage,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier
                                .pagerTabIndicatorOffset(pagerState, tabPositions)
                                .width(10.dp),
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
                                        style = MaterialTheme.typography.bodyMedium,
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
                    userScrollEnabled = false,

                    ) {
                    tabRowItems[pagerState.currentPage].screen()
                }
            }


        }

    }

}

@Composable
fun StandingScreen() {

}

@Composable
fun ScorerScreen() {

}