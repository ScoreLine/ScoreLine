package com.swayy.matches.ui

import android.os.Build
import androidx.annotation.RequiresApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.swayy.matches.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MatchesScreen() {
    val coroutineScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 56.dp)
    ) {


        Column(modifier = Modifier.fillMaxSize()) {
            Header()
            Spacer(modifier = Modifier.height(8.dp))
            val tabRowItems = listOf(
                TabRowItem(
                    title = getFormattedDayBeforeYesterday(3),
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = getFormattedDayBeforeYesterday(2),
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = "Yesterday",
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = "Today",
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = "Tomorrow",
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = getFormattedDayAfterTomorrow(2),
                    screen = { TabScreen() }
                ),
                TabRowItem(
                    title = getFormattedDayAfterTomorrow(3),
                    screen = { TabScreen() }
                ),
            )

            val pagerState = rememberPagerState(initialPage = 3)

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

data class Car(val name: String, val manufacturer: String)

@Composable
fun TabScreen() {

    val carList = listOf(
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
        Car("Honda Civic", "Honda"),
        Car("Toyota Corolla", "Toyota"),
        Car("Ford Mustang", "Ford"),
    )
    LazyColumn {
        items(carList) { car ->
            CarItem(car)
        }
    }
}

@Composable
fun CarItem(car: Car) {
    Column {
        Spacer(modifier = Modifier.height(14.dp))
        Text(
            text = car.toString(),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedDayBeforeYesterday(sub: Long): String {
    val formatter = DateTimeFormatter.ofPattern("E d MMM", Locale.ENGLISH)
    val dayBeforeYesterday = LocalDate.now().minusDays(sub)
    return dayBeforeYesterday.format(formatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun getFormattedDayAfterTomorrow(sub: Long): String {
    val formatter = DateTimeFormatter.ofPattern("E d MMM", Locale.ENGLISH)
    val dayBeforeYesterday = LocalDate.now().plusDays(sub)
    return dayBeforeYesterday.format(formatter)
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .padding(start = 12.dp, end = 14.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        androidx.compose.material3.Text(
            text = "ScoreLine",
            style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(start = 10.dp)
        )

        Row(modifier = Modifier.align(Alignment.CenterVertically)) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_access_time_24),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_calendar_today_24),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_search_24),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(20.dp))
            Icon(
                painter = painterResource(id = R.drawable.baseline_more_vert_24),
                contentDescription = null,
            )
        }

    }

}
