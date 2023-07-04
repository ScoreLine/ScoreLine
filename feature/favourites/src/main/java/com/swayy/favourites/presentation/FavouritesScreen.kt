package com.swayy.favourites.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jsoup.Jsoup
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@Composable
fun FavouritesScreen() {
    var clubs by remember { mutableStateOf(emptyList<Club>()) }

    LaunchedEffect(Unit) {
        clubs = scrapeClubs()
    }
    LazyColumn {
        items(clubs) { club ->
            ClubItem(club)
        }
    }
}

@Composable
fun ClubItem(club: Club) {
    Row {
        val imagePainter = rememberImagePainter(
            data = club.imageUrl,
            builder = {
                transformations(CircleCropTransformation())
            }
        )


        Image(
            painter = imagePainter,
            contentDescription = club.name,
            modifier = Modifier
                .size(48.dp)
                .padding(8.dp),
            contentScale = ContentScale.Fit
        )

        androidx.compose.material3.Text(
            text = club.name,
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,

            )
    }


}

data class Club(val name: String, val imageUrl: String, val websiteUrl: String)


suspend fun scrapeClubs(): List<Club> = withContext(Dispatchers.IO) {
    val url = "https://www.premierleague.com/clubs"
    val clubs = mutableListOf<Club>()

    try {
        val doc = Jsoup.connect(url).get()
        val clubElements = doc.select("li.clubList__club")

        for (clubElement in clubElements) {
            val linkElement = clubElement.selectFirst("a.clubList__link")
            val imageElement = clubElement.selectFirst("img.badge-image")
            val nameElement = clubElement.selectFirst("span.name")

            val club = nameElement?.let {
                imageElement?.let { it1 ->
                    linkElement?.let { it2 ->
                        Club(
                            it.text(),
                            it1.attr("src"),
                            it2.attr("href")
                        )
                    }
                }
            }
            clubs.add(club!!)
        }

        clubs
    } catch (e: IOException) {
        e.printStackTrace()
        throw e
    }
}