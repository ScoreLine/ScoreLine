package com.swayy.core.core

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.swayy.core.core.components.addInterstitialCallbacks
import com.swayy.core.core.components.loadInterstitial
import com.swayy.core.core.components.showInterstitial
import com.swayy.core.domain.model.WebMatch

@Composable
fun MatchItemCard(
    match: WebMatch,
    navigateMatchDetails: (String) -> Unit,
    context:Context
) {
    loadInterstitial(context)
    // add the interstitial ad callbacks
    addInterstitialCallbacks(context)

    fun convertToEncodedString(input: String): String {
        val encodedString = input.replace("/", "_SLASH_")
        return encodedString
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .padding(start = 8.dp, end = 8.dp)
            .clickable(onClick = {
                loadInterstitial(context)
                showInterstitial(context)
                navigateMatchDetails(convertToEncodedString(match.link))
            }),
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
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
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
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = match.matchProgress,
                        fontSize = 13.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.onSurface,

                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = match.homeScore,
                        fontSize = 16.sp,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.onSurface,
                    )
                }
                Image(
                    painter = rememberImagePainter(match.awayTeamImageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp)
                        .align(Alignment.CenterVertically)
                )
                Text(
                    text = match.awayTeam,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
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