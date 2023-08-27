package com.swayy.news

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Surface
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.io.IOException
import java.net.URL
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.squareup.picasso.Picasso
import com.swayy.core.R
import com.swayy.news.presentation.NewsViewModel
import com.swayy.news.presentation.TrendingNewsScreen
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalPagerApi::class)
@Composable
fun NewsScreen(
    newsViewModel: NewsViewModel = hiltViewModel(),
    navigateNewsDetails: (String) -> Unit,
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
                    modifier = Modifier.padding(top = 50.dp, start = 12.dp, end = 12.dp),
                ) {
                    androidx.compose.material3.Text(
                        text = "ScoreLine",
                        style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
                    )
                    Spacer(modifier = Modifier.weight(2f))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_download_24),
                        contentDescription = "",
                        modifier = Modifier
                            .padding(end = 0.dp),
                        tint = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary

                    )
                }

            }

            Spacer(modifier = Modifier.height(0.dp))

            val tabRowItems = listOf(
                TabRowItem(
                    title = "Latest",
                    screen = {
                        TrendingNewsScreen(newsViewModel, navigateNewsDetails)
                    }
                ),
                TabRowItem(
                    title = "Premier League",
                    screen = {
                        TrendingNewsScreen(newsViewModel, navigateNewsDetails)
                    }
                ),
                TabRowItem(
                    title = "Laliga",
                    screen = {
                        TrendingNewsScreen(newsViewModel, navigateNewsDetails)
                    }

                ),
                TabRowItem(
                    title = "Serie A",
                    screen = {
                        TrendingNewsScreen(newsViewModel, navigateNewsDetails)
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
                            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
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

    }

}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)
