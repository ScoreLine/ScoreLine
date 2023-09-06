package com.swayy.core.core

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Typeface
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import com.squareup.picasso.Picasso
import com.squareup.picasso.Transformation
import com.swayy.core.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Composable
fun LineupScreenTwo(
    link:String,
    background: Color,
    context: Context,
    hometeam: String,
    awayteam: String
) {

    val matchLine = remember { mutableStateListOf<Lineup>() }

    val teamA = matchLine.dropLast(11)
    val teamB = matchLine.drop(11)

    val formation = "4-3-3"

    LaunchedEffect(Unit) {
        fetchMatchLineup(link, matchLine)
    }

    if (matchLine.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.nolineup),
                    contentDescription = "",
                    modifier = Modifier
                        .size(180.dp)
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "No lineup available yet",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = "The coaches are not yet sure who should play",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center
                )
            }

        }
    } else {
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
                    teamA.forEach {

                        Log.e("tactic",it.tacticHome)
                        drawContext.canvas.nativeCanvas.apply {
                            val font: Typeface? =
                                ResourcesCompat.getFont(context, R.font.and)

                            val paint = Paint().apply {
                                color = Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                typeface = font
                                isAntiAlias = true
                                textSize = 16.sp.toPx()
                            }

                            drawText(hometeam, canvasWidth * 0.2f, canvasHeight * 0.02f, paint)
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            val font: Typeface? =
                                ResourcesCompat.getFont(context, R.font.and)

                            val paint = Paint().apply {
                                color = Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                typeface = font
                                isAntiAlias = true
                                textSize = 16.sp.toPx()
                            }

                            drawText(it.tacticHome, canvasWidth * 0.89f, canvasHeight * 0.02f, paint)
                        }

                        if (it.tacticHome == "3-2-4-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "4-2-3-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }
                        if (it.tacticHome == "4-4-1-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "4-1-3-2") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 2
                            val playersAfterLastt = players.takeLast(2)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.45f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "4-3-1-2") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 2
                            val playersAfterLastt = players.takeLast(2)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.45f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "4-1-4-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "3-4-1-2") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 2
                            val playersAfterLastt = players.takeLast(2)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.45f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "3-4-2-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.37f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "4-3-3") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 3
                            val playersAfterLastt = players.takeLast(3)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.38f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.26f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }


                        if (it.tacticHome == "5-2-3") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 3
                            val playersAfterLastt = players.takeLast(3)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.38f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.34f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }
                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "5-3-2") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 2
                            val playersAfterLastt = players.takeLast(2)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.38f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.34f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticHome == "5-4-1") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.34f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }
                        if (it.tacticHome == "4-4-2") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 2
                            val playersAfterLastt = players.takeLast(2)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.38f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }
                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }
                        if (it.tacticHome == "3-4-3") {
                            val players = teamA

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCountt = 3
                            val playersAfterLastt = players.takeLast(3)
                            val defenderSpacingt =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountt + 1)
                            playersAfterLastt.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingt * (index + 1)
                                val yone = canvasHeight * 0.38f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.27f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val defendersCountdefenseone = 3
                            val playersWithoutLastdefone = players.dropLast(6)
                            val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                            val defenderSpacingdefone =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                            playersAfterLastdefone.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.18f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }
                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.08f)
                            teamA.take(1).forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                    //away team
                    teamB.forEach {
                        drawContext.canvas.nativeCanvas.apply {
                            val font: Typeface? =
                                ResourcesCompat.getFont(context, R.font.and)

                            val paint = Paint().apply {
                                color = Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                typeface = font
                                isAntiAlias = true
                                textSize = 16.sp.toPx()
                            }

                            drawText(awayteam, canvasWidth * 0.2f, canvasHeight * 0.99f, paint)
                        }
                        drawContext.canvas.nativeCanvas.apply {
                            val font: Typeface? =
                                ResourcesCompat.getFont(context, R.font.and)

                            val paint = Paint().apply {
                                color = Color.White.toArgb()
                                textAlign = Paint.Align.CENTER
                                typeface = font
                                isAntiAlias = true
                                textSize = 16.sp.toPx()
                            }

                            drawText(it.tacticAway, canvasWidth * 0.89f, canvasHeight * 0.99f, paint)
                        }
                        if (it.tacticAway == "3-2-4-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }
                        }

                        if (it.tacticAway == "4-2-3-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }
                        if (it.tacticAway == "4-4-1-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "4-1-3-2") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 2
                            val playersAfterLasts = players.takeLast(2)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "4-3-1-2") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 2
                            val playersAfterLasts = players.takeLast(2)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "4-1-4-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }
                        if (it.tacticAway == "3-4-1-2") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 2
                            val playersAfterLasts = players.takeLast(2)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "3-4-2-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacing * (index + 1)
                                val yone = canvasHeight * 0.63f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.73f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "4-3-3") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 3
                            val playersAfterLasts = players.takeLast(3)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.62f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.72f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "5-2-3") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 3
                            val playersAfterLasts = players.takeLast(3)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.62f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.72f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "5-3-2") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 2
                            val playersAfterLasts = players.takeLast(2)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.62f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.72f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "5-4-1") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val lastPlayer = players[players.size - 1]
                            val forwardsCount = 1
                            val forwardSpacing =
                                (canvasWidth - goalPostWidth * 2) / (forwardsCount + 1)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.66f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "4-4-2") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 2
                            val playersAfterLasts = players.takeLast(2)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.62f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.72f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
                                    goalkeeperPosition,
                                    playerTextSize,
                                    context
                                )
                            }

                        }

                        if (it.tacticAway == "3-4-3") {
                            val players = teamB

                            val playerTextSize = 13.sp.toPx()

                            //get the forward
                            val defendersCounts = 3
                            val playersAfterLasts = players.takeLast(3)
                            val defenderSpacings =
                                (canvasWidth - goalPostWidth * 2) / (defendersCounts + 1)
                            playersAfterLasts.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacings * (index + 1)
                                val yone = canvasHeight * 0.62f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
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
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdef * (index + 1)
                                val yone = canvasHeight * 0.72f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val defendersCountdefenseone = 3
                            val playersWithoutLastdefone = players.dropLast(6)
                            val playersAfterLastdefone = playersWithoutLastdefone.takeLast(3)
                            val defenderSpacingdefone =
                                (canvasWidth - goalPostWidth * 2) / (defendersCountdefenseone + 1)
                            playersAfterLastdefone.forEachIndexed { index, playerData ->
                                val player = playerData
                                val xone = goalPostWidth + defenderSpacingdefone * (index + 1)
                                val yone = canvasHeight * 0.81f
                                val playerOffset = Offset(xone, yone)
                                drawPlayer(playerData, playerOffset)
                                drawPlayerName(
                                    player.name,
                                    playerOffset,
                                    playerTextSize,
                                    context
                                )
                            }

                            val goalkeeperPosition = Offset((canvasWidth / 2), canvasHeight * 0.91f)
                            teamB?.take(1)?.forEach {
                                drawPlayer(it, goalkeeperPosition)
                                drawPlayerName(
                                    it.name,
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

}


private suspend fun loadImage(url: String): ImageBitmap = withContext(Dispatchers.IO) {
    val bitmap = Picasso.get()
        .load(url)
        .resize(100, 100)
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

private fun DrawScope.drawPlayer(player: Lineup, position: Offset) {

    val imageUrl = player.image

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
        val font: Typeface? = ResourcesCompat.getFont(context, R.font.and)

        val paint = android.graphics.Paint().apply {
            color = Color.White.toArgb()
            textSize = text
            textAlign = android.graphics.Paint.Align.CENTER
            typeface = font
            isAntiAlias = true
        }
        val textOffsetY = text * 3.7f

        fun getFirstName(fullName: String): String {
            return fullName.split(" ").lastOrNull() ?: fullName
        }

        val cleanedName = getFirstName(name)

        drawText(cleanedName, position.x, position.y + textOffsetY, paint)
    }
}