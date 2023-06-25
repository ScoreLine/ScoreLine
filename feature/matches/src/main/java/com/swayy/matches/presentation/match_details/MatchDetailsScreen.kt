package com.swayy.matches.presentation.match_details

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.text.Layout
import android.text.StaticLayout
import android.util.Log
import android.view.View
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
import androidx.compose.material.CircularProgressIndicator
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
import androidx.compose.ui.draw.BlurredEdgeTreatment.Companion.Rectangle
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.PaintingStyle.Companion.Stroke
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultBlendMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.graphics.withSave
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.ImagePainter
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
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.swayy.core.R
import com.swayy.core_network.model.lineup.Player
import com.swayy.core_network.model.lineup.StartXI
import com.swayy.matches.presentation.MatchViewmodel
import com.swayy.matches.presentation.TabRowItem
import com.swayy.matches.presentation.TabScreen
import com.swayy.matches.presentation.getFormattedDayBeforeYesterday
import com.swayy.matches.presentation.state.LineupState
import com.swayy.matches.presentation.state.MatchState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


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

                    if (it.formation == "3-2-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 4
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(4)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-2-3-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 3
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(3)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(4)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }
                    if (it.formation == "4-4-1-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(2)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-1-3-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 2
                        val playersAfterLastt = players.takeLast(2)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.45f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //attacking midfielders
                        val defendersCount = 3
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(3)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 1
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(1)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-3-1-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 2
                        val playersAfterLastt = players.takeLast(2)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.45f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //attacking midfielders
                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-1-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 4
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(4)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 1
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(1)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "3-4-1-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 2
                        val playersAfterLastt = players.takeLast(2)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.45f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //attacking midfielders
                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "3-4-2-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 2
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(2)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.37f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-3-3") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 3
                        val playersAfterLastt = players.takeLast(3)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.38f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }


                    if (it.formation == "5-2-3") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 3
                        val playersAfterLastt = players.takeLast(3)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.38f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "5-3-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCountt = 2
                        val playersAfterLastt = players.takeLast(2)
                        val defenderSpacingt =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                        playersAfterLastt.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingt * (index + 1)
                            val yone = canvasHeight * 0.38f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(2)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "5-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.45f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(1)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.27f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.18f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //away team
                lineupState.lineup.takeLast(1).forEach {
                    if (it.formation == "3-2-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //attacking midfielders
                        val defendersCount = 4
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(4)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }
//
                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }
                    }

                    if (it.formation == "4-2-3-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        val defendersCount = 3
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(3)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(4)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }
                    if (it.formation == "4-4-1-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(2)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-1-3-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 2
                        val playersAfterLasts = players.takeLast(2)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCount = 3
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(3)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 1
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(1)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-3-1-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 2
                        val playersAfterLasts = players.takeLast(2)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-1-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        val defendersCount = 4
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(4)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 1
                        val playersWithoutLastdef = players.dropLast(5)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(1)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }
                    if (it.formation == "3-4-1-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 2
                        val playersAfterLasts = players.takeLast(2)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCount = 1
                        val playersWithoutLast = players.dropLast(2)
                        val playersAfterLast = playersWithoutLast.takeLast(1)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "3-4-2-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        val defendersCount = 2
                        val playersWithoutLast = players.dropLast(1)
                        val playersAfterLast = playersWithoutLast.takeLast(2)
                        val defenderSpacing =
                            (canvasWidth - goalPostWidth * 2) / (defendersCount + 1)
                        playersAfterLast.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacing * (index + 1)
                            val yone = canvasHeight * 0.63f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.73f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 3
                        val playersWithoutLastdefone = players.dropLast(7)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "4-3-3") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 3
                        val playersAfterLasts = players.takeLast(3)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.62f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.72f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 4
                        val playersWithoutLastdefone = players.dropLast(6)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(4)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "5-2-3") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 3
                        val playersAfterLasts = players.takeLast(3)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.62f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 2
                        val playersWithoutLastdef = players.dropLast(3)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(2)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.72f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "5-3-2") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val defendersCounts = 2
                        val playersAfterLasts = players.takeLast(2)
                        val defenderSpacings =
                            (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                        playersAfterLasts.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacings * (index + 1)
                            val yone = canvasHeight * 0.62f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        //defensive midfielders
                        val defendersCountdefense = 3
                        val playersWithoutLastdef = players.dropLast(2)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(3)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.72f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                    if (it.formation == "5-4-1") {
                        val players = it.startXI

                        val playerTextSize = 13.sp.toPx()

                        //get the forward
                        val lastPlayer = players[players.size - 1].player
                        val forwardsCount = 1
                        val forwardSpacing = (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
                        val x = goalPostWidth + forwardSpacing
                        val y = canvasHeight * 0.55f
                        val forwardPosition = Offset(x, y)
                        drawPlayer(lastPlayer, forwardPosition)
                        drawPlayerName(
                            lastPlayer.name,
                            forwardPosition,
                            playerTextSize,
                            context
                        )

                        //defensive midfielders
                        val defendersCountdefense = 4
                        val playersWithoutLastdef = players.dropLast(1)
                        val playersAfterLastdef = playersWithoutLastdef.takeLast(4)
                        val defenderSpacingdef =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefense + 1)
                        playersAfterLastdef.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                            val yone = canvasHeight * 0.72f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val defendersCountdefenseone = 5
                        val playersWithoutLastdefone = players.dropLast(5)
                        val playersAfterLastdefone = playersWithoutLastdefone.takeLast(5)
                        val defenderSpacingdefone =
                            (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                        playersAfterLastdefone.forEachIndexed { index, playerData ->
                            val player = playerData.player
                            val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                            val yone = canvasHeight * 0.81f
                            val playerOffset = Offset(xone, yone)
                            drawPlayer(playerData.player, playerOffset)
                            drawPlayerName(
                                player.name,
                                playerOffset,
                                playerTextSize,
                                context
                            )
                        }

                        val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                        val goalkeeper = it.startXI.find { it.player.pos == "G" }
                        goalkeeper?.let {
                            drawPlayer(it.player, goalkeeperPosition)
                            drawPlayerName(
                                it.player.name,
                                goalkeeperPosition,
                                playerTextSize,
                                context
                            )
                        }

                    }

                }

            }
        }

    }
}

//test

private suspend fun loadImage(url: String): ImageBitmap = withContext(Dispatchers.IO) {
    val bitmap = Picasso.get()
        .load(url)
        .resize(100,100)
        .centerCrop()
        .transform(RoundedTransformation(radius = 100f)) // Adjust the radius as desired
        .get()
    bitmap.asImageBitmap()
}

class RoundedTransformation(private val radius: Float) : Transformation {
    override fun transform(source: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(source.width, source.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = android.graphics.Paint().apply {
            isAntiAlias = true
            shader = BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        }

        val rect = RectF(0f, 0f, source.width.toFloat(), source.height.toFloat())
        canvas.drawRoundRect(rect, radius, radius, paint)

        if (source != output) {
            source.recycle()
        }

        return output
    }

    override fun key(): String = "rounded(radius=$radius)"
}
private fun DrawScope.drawPlayer(player: Player,position: Offset) {

    val imageUrl = "https://media.api-sports.io/football/players/${player.id}.png"

    val playerRadius = 23.dp.toPx()
    val circleCenter = position

    // Load the image using Picasso
    val imageBitmap = runBlocking {
        loadImage(imageUrl)
    }

    // Draw the loaded image inside the circle
    drawImage(
        image = imageBitmap,
        topLeft = Offset(circleCenter.x - playerRadius, circleCenter.y - playerRadius)
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

        val paint = android.graphics.Paint().apply {
            color = Color.White.toArgb()
            textSize = text
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = font
            isAntiAlias = true
        }
        val textOffsetY = text * 3.7f

        fun getFirstName(fullName: String): String {
            return fullName.split(" ").firstOrNull() ?: fullName
        }

        val cleanedName = getFirstName(name)

        drawText(cleanedName, position.x, position.y + textOffsetY, paint)
    }
}

