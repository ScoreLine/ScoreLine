package com.swayy.matches.presentation.match_details

import android.content.Context
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.util.Log
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.kaajjo.libresudoku.ui.components.collapsing_topappbar.CollapsingTitle
import com.kaajjo.libresudoku.ui.components.collapsing_topappbar.CollapsingTopAppBar
import com.kaajjo.libresudoku.ui.components.collapsing_topappbar.rememberTopAppBarScrollBehavior
import com.swayy.core.R
import com.swayy.core_network.model.lineup.Player
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.matches.presentation.MatchViewmodel
import com.swayy.matches.presentation.TabRowItem
import com.swayy.matches.presentation.TabScreen
import com.swayy.matches.presentation.getFormattedDayBeforeYesterday
import com.swayy.matches.presentation.state.LineupState
import com.swayy.matches.presentation.state.MatchState
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MatchDetailsScreen(
    id: Int,
    navigateBack: () -> Unit,
    viewModel: MatchViewmodel = hiltViewModel(),
    lineupViewmodel: LineupViewmodel = hiltViewModel(),
    date: String
) {
    Log.e("Tag", "THIS IS THE ID ${id}")

    val matchState = viewModel.matches.value
    val lineupState = lineupViewmodel.lineup.value

    LaunchedEffect(key1 = true) {
        viewModel.getMatches(date = date)
    }

    LaunchedEffect(key1 = true) {
        lineupViewmodel.getLineup(fixture = id)
    }

    Box(modifier = Modifier.padding(top = 40.dp)) {
        Column {
            Row(modifier = Modifier.padding(start = 10.dp, end = 12.dp)) {

                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(com.swayy.core.R.drawable.ic_round_arrow_back_24),
                        contentDescription = null
                    )
                }

                Spacer(modifier = Modifier.weight(2f))

                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(com.swayy.matches.R.drawable.baseline_star_outline_24),
                        contentDescription = null
                    )
                }

                IconButton(onClick = navigateBack) {
                    Icon(
                        painter = painterResource(com.swayy.matches.R.drawable.baseline_more_vert_24),
                        contentDescription = null
                    )
                }

            }

            Column {
                val matchesWithSameLeagueId = matchState.matches.filter { it.fixture.id == id }

                matchesWithSameLeagueId.forEach { match ->

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
                            val image = match.teams.home.logo

                            //test
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = image)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)
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
                                text = match.teams.home.name,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center
                            )
                        }

                        Column {
                            val scorehome =
                                (match.score.fulltime.home as? Double)?.toInt()
                                    ?: 0
                            val scoreaway =
                                (match.score.fulltime.away as? Double)?.toInt()
                                    ?: 0


                            Text(
                                text = scorehome.toString() + " - " + scoreaway,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 30.sp,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = match.fixture.status.long,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontSize = 12.sp,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.width(100.dp)
                        ) {
                            val image = match.teams.away.logo

                            //test
                            val painter = rememberAsyncImagePainter(
                                ImageRequest.Builder(LocalContext.current)
                                    .data(data = image)
                                    .apply(block = fun ImageRequest.Builder.() {
                                        crossfade(true)
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
                                text = match.teams.away.name,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.CenterHorizontally),
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center

                            )
                        }
                    }

                    val pagerState = rememberPagerState(initialPage = 0)
                    val coroutineScope = rememberCoroutineScope()
                    val context = LocalContext.current

                    val tabRowItems = listOf(
                        TabRowItem(
                            title = "Facts",
                            screen = {
                                LineupScreen(
                                    lineupState,
                                    MaterialTheme.colorScheme.primary,
                                    context,
                                    match.teams.away.name,
                                    match.teams.home.name
                                )
                            }
                        ),
                        TabRowItem(
                            title = "Lineup",
                            screen = {
                                LineupScreen(
                                    lineupState,
                                    MaterialTheme.colorScheme.primary,
                                    context,
                                    match.teams.away.name,
                                    match.teams.home.name
                                )
                            }
                        ),
                        TabRowItem(
                            title = "Table",
                            screen = {
                                LineupScreen(
                                    lineupState,
                                    MaterialTheme.colorScheme.primary,
                                    context,
                                    match.teams.away.name,
                                    match.teams.home.name
                                )
                            }
                        ),
                        TabRowItem(
                            title = "Stats",
                            screen = {
                                LineupScreen(
                                    lineupState,
                                    MaterialTheme.colorScheme.primary,
                                    context,
                                    match.teams.away.name,
                                    match.teams.home.name
                                )
                            }
                        ),
                        TabRowItem(
                            title = "H2H",
                            screen = {
                                LineupScreen(
                                    lineupState,
                                    MaterialTheme.colorScheme.primary,
                                    context,
                                    match.teams.away.name,
                                    match.teams.home.name
                                )
                            }
                        ),
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Column(
                        modifier = Modifier
                            .padding(0.dp)
                    ) {
                        androidx.compose.material.TabRow(
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
                            userScrollEnabled = false
                        ) {
                            tabRowItems[pagerState.currentPage].screen()
                        }
                    }


                }

            }

        }

    }

}


@Composable
fun LineupScreen(
    lineupState: LineupState,
    background: Color,
    context: Context,
    hometeam: String,
    awayteam: String
) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        item {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1000.dp)
            ) {
                val canvasWidth = size.width
                val canvasHeight = size.height

                // Background
                drawRect(color = background, size = Size(canvasWidth, canvasHeight))


                drawRect(
                    color = Color.Green.copy(alpha = 0.04f),
                    topLeft = Offset(0f, 0f),
                    size = Size(canvasWidth, 100.dp.toPx()),
                )

                drawRect(
                    color = Color.Green.copy(alpha = 0.04f),
                    topLeft = Offset(0f, 200.dp.toPx()),
                    size = Size(canvasWidth, 100.dp.toPx()),
                )
                drawRect(
                    color = Color.Green.copy(alpha = 0.04f),
                    topLeft = Offset(0f, 400.dp.toPx()),
                    size = Size(canvasWidth, 100.dp.toPx()),
                )
                drawRect(
                    color = Color.Green.copy(alpha = 0.04f),
                    topLeft = Offset(0f, 600.dp.toPx()),
                    size = Size(canvasWidth, 100.dp.toPx()),
                )
                drawRect(
                    color = Color.Green.copy(alpha = 0.04f),
                    topLeft = Offset(0f, 800.dp.toPx()),
                    size = Size(canvasWidth, 100.dp.toPx()),
                )

                // Goal Posts
                val goalPostWidth = canvasWidth * 0.03f

                //topline
                drawLine(
                    color = Color.White.copy(alpha = 0.4f),
                    start = Offset(canvasWidth * 0.03f, canvasHeight * 0.03f),
                    end = Offset(canvasWidth * 0.97f, canvasHeight * 0.03f),
                    strokeWidth = 5f
                )

                //bottom line
                drawLine(
                    color = Color.White.copy(alpha = 0.4f),
                    start = Offset(canvasWidth * 0.03f, canvasHeight * 0.97f),
                    end = Offset(canvasWidth * 0.97f, canvasHeight * 0.97f),
                    strokeWidth = 5f
                )

                //left line
                drawLine(
                    color = Color.White.copy(alpha = 0.4f),
                    start = Offset(goalPostWidth, canvasHeight * 0.03f),
                    end = Offset(goalPostWidth, canvasHeight * 0.97f),
                    strokeWidth = 5f
                )

                //right line
                drawLine(
                    color = Color.White.copy(alpha = 0.4f),
                    start = Offset(canvasWidth - goalPostWidth, canvasHeight * 0.03f),
                    end = Offset(canvasWidth - goalPostWidth, canvasHeight * 0.97f),
                    strokeWidth = 5f
                )

                // Circle at the Middle
                val circleRadius = minOf(canvasWidth, canvasHeight) * 0.1f
                val circleCenter = Offset(canvasWidth / 2, canvasHeight / 2)

                drawCircle(
                    color = Color.White.copy(alpha = 0.4f),
                    style = Stroke(width = 5f),
                    radius = circleRadius,
                    center = circleCenter
                )

                // Additional Lines
                val lineWidth = 5f

                // Midfield Line
                drawLine(
                    color = Color.White.copy(alpha = 0.4f),
                    start = Offset(canvasWidth * 0.03f, canvasHeight / 2),
                    end = Offset(canvasWidth * 0.97f, canvasHeight / 2),
                    strokeWidth = lineWidth
                )

                // Penalty Areas
                val penaltyAreaWidth = canvasWidth * 0.6f
                val penaltyAreaHeight = canvasHeight * 0.1f

                val penaltyAreaWidthone = canvasWidth * 0.3f
                val penaltyAreaHeightone = canvasHeight * 0.05f

                //big top rectangle
                drawRect(
                    color = Color.White.copy(alpha = 0.4f),
                    topLeft = Offset(
                        (canvasWidth - penaltyAreaWidth) / 2,
                        canvasHeight * 0.03f
                    ),
                    size = Size(penaltyAreaWidth, penaltyAreaHeight),
                    style = Stroke(width = lineWidth)
                )
                //test

                val arcRadius = penaltyAreaHeight / 3f

                val path = Path().apply {
                    val arcCenter = Offset(canvasWidth / 2, canvasHeight * 0.12f)

                    arcTo(
                        Rect(
                            left = arcCenter.x - arcRadius,
                            top = arcCenter.y - arcRadius / 2,
                            right = arcCenter.x + arcRadius,
                            bottom = arcCenter.y + arcRadius
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = 180f,
                        forceMoveTo = false
                    )

                }

                val pathbottom = Path().apply {
                    val arcCenter = Offset(canvasWidth / 2, canvasHeight * 0.861f)

                    arcTo(
                        Rect(
                            left = arcCenter.x - arcRadius,
                            top = arcCenter.y - arcRadius / 2,
                            right = arcCenter.x + arcRadius,
                            bottom = arcCenter.y + arcRadius
                        ),
                        startAngleDegrees = 0f,
                        sweepAngleDegrees = -180f,
                        forceMoveTo = false
                    )

                }

                drawPath(
                    path = path,
                    color = Color.White.copy(alpha = 0.4f),
                    style = Stroke(width = lineWidth)
                )

                drawPath(
                    path = pathbottom,
                    color = Color.White.copy(alpha = 0.4f),
                    style = Stroke(width = lineWidth)
                )

                ///big bottom rectangle
                drawRect(
                    color = Color.White.copy(alpha = 0.4f),
                    topLeft = Offset(
                        (canvasWidth - penaltyAreaWidthone) / 2,
                        canvasHeight * 0.03f
                    ),
                    size = Size(penaltyAreaWidthone, penaltyAreaHeightone),
                    style = Stroke(width = lineWidth)
                )

                //small top rectangle
                drawRect(
                    color = Color.White.copy(alpha = 0.4f),
                    topLeft = Offset(
                        (canvasWidth - penaltyAreaWidth) / 2,
                        ((canvasHeight - penaltyAreaHeight) * 0.967f)
                    ),
                    size = Size(penaltyAreaWidth, penaltyAreaHeight),
                    style = Stroke(width = lineWidth)
                )

                //small bottom rectangle
                drawRect(
                    color = Color.White.copy(alpha = 0.4f),
                    topLeft = Offset(
                        (canvasWidth - penaltyAreaWidthone) / 2,
                        ((canvasHeight - penaltyAreaHeightone) * 0.967f)
                    ),
                    size = Size(penaltyAreaWidthone, penaltyAreaHeightone),
                    style = Stroke(width = lineWidth)
                )

                //home team
                lineupState.lineup.take(1).forEach {

                    val players = it.startXI

                    val playerTextSize = 13.sp.toPx()

                    if (players.size >= 2) {
                        val secondLastPlayer = players[players.size - 2].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)

                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.4f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(forwardPosition)
                        drawPlayerName(
                            secondLastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )
                    } else {
                        // Handle the case when there are not enough players in the list
                    }




//                    val goalkeeperPosition = Offset((canvasWidth / 2), penaltyAreaHeightone)
//                    val goalkeeper = it.startXI.find { it.player.pos == "G" }
//                    goalkeeper?.let {
//                        drawPlayer(it, goalkeeperPosition)
//                        drawPlayerName(
//                            it.player.name,
//                            goalkeeperPosition,
//                            playerTextSize,
//                            context
//                        )
//                    }

//                        // Defenders
//                        val defendersCount = 4
//                        val defenderSpacing = (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
//                        val defenders = it.startXI.filter { it.player.pos == "D" }
//                        defenders.forEachIndexed { index, defender ->
//                            val x = goalPostWidth + defenderSpacing * (index + 1)
//                            val y = canvasHeight * 0.16f
//                            val defenderPosition = Offset(x, y)
//                            drawPlayer(defender, defenderPosition)
//                            drawPlayerName(
//                                defender.player.name,
//                                defenderPosition,
//                                playerTextSize,
//                                context
//                            )
//
//                        }
//
//                        // Midfielders
//                        val midfieldersCount = 4
//                        val midfielderSpacing =
//                            (canvasWidth - goalPostWidth * 2) / (midfieldersCount + 1)
//                        val midfielders = it.startXI.filter { it.player.pos == "M" }
//                        midfielders.forEachIndexed { index, midfielder ->
//                            val x = goalPostWidth + midfielderSpacing * (index + 1)
//                            val y = canvasHeight * 0.28f
//                            val midfielderPosition = Offset(x, y)
//                            drawPlayer(midfielder, midfielderPosition)
//                            drawPlayerName(
//                                midfielder.player.name,
//                                midfielderPosition,
//                                playerTextSize,
//                                context
//                            )
//                        }


                }

            }
        }


    }
}

private fun DrawScope.drawPlayer( position: Offset) {
    val playerRadius = 23.dp.toPx()
    drawCircle(
        color = Color.White,
        radius = playerRadius,
        center = position
    )

}


private fun DrawScope.drawPlayerName(
    name: String,
    position: Offset,
    text: Float,
    context: Context
) {
    drawContext.canvas.nativeCanvas.apply {
        val font: Typeface? = ResourcesCompat.getFont(context, com.swayy.matches.R.font.and)

        val paint = Paint().apply {
            color = Color.White.toArgb()
            textSize = text
            textAlign = Paint.Align.CENTER
            typeface = font
            isAntiAlias = true
        }
        val textOffsetY = text * 3.7f

        drawText(name, position.x, position.y + textOffsetY, paint)
    }
}

