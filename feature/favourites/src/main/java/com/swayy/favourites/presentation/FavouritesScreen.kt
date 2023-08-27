package com.swayy.favourites.presentation

import android.content.pm.ApplicationInfo
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.swayy.core.R
import com.swayy.favourites.presentation.components.TeamsScreen
import com.swayy.shared.domain.model.ClubItem
import com.swayy.shared.presentation.ClubsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FavouritesScreen(
    clubsViewModel: ClubsViewModel = hiltViewModel()
) {
    val state = clubsViewModel.clubs.value
    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxWidth()) {
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
                        text = "Favourites",
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
                    title = "Teams",
                    screen = {
                        TeamsScreen(state)
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

        if (state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = MaterialTheme.colorScheme.primary
            )
        }

        if (state.error.isNotBlank()) {
            androidx.compose.material.Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center)
            )
        }
    }

}

data class TabRowItem(
    val title: String,
    val screen: @Composable () -> Unit,
)

@Composable
fun BannerAdView() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            val adView = AdView(context)
            adView.setAdSize(AdSize.LARGE_BANNER)

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




