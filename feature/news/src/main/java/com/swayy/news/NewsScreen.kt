package com.swayy.news

import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
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
import com.squareup.picasso.Picasso
import com.swayy.core.R
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


data class NewsItem(
    val title: String,
    val imageUrl: String,
    val description: String,
    val url: String
)

@Composable
fun NewsScreen() {
    val newsList = remember { mutableStateListOf<NewsItem>() }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        isLoading.value = true
        withContext(Dispatchers.IO) {
            fetchNewsFromWebsite(newsList)
        }
        isLoading.value = false
    }

    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            // Handle the result here if needed
        }

    Box(modifier = Modifier.fillMaxSize()) {

        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
        } else {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 20.dp)
                        .statusBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    androidx.compose.material3.Text(
                        text = "News",
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Spacer(modifier = Modifier.weight(2f))
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_filter_list_24),
                        contentDescription = "",
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .padding(end = 12.dp)

                    )
                }

                Spacer(modifier = Modifier.height(14.dp))
                NewsList(newsList = newsList)
            }

        }
        }


}

@Composable
fun NewsList(newsList: List<NewsItem>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        itemsIndexed(newsList) { index, item ->
            NewsItemCard(item)
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem) {
    Column(

    ) {
        val imageBitmap = remember(newsItem.imageUrl) { mutableStateOf<ImageBitmap?>(null) }

        LaunchedEffect(newsItem.imageUrl) {
            loadImageBitmap(newsItem.imageUrl)?.let {
                imageBitmap.value = it
            }
        }

        imageBitmap.value?.let { bitmap ->
            Image(
                bitmap = bitmap,
                contentDescription = newsItem.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .padding(start = 8.dp, end = 8.dp)
                    .clip(RoundedCornerShape(14.dp))
            )
        }

        Text(
            text = newsItem.title,
            modifier = Modifier.padding(10.dp),
            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp

        )
    }
}


fun fetchNewsFromWebsite(newsList: MutableList<NewsItem>) {
    try {
        val url = "https://www.goal.com/en-ke"
        val document: Document = Jsoup.connect(url).get()

        val articleElements: Elements = document.select("article")
        val maxNewsCount = minOf(articleElements.size, 2)

        for (i in 0 until maxNewsCount) {
            val element: Element = articleElements[i]
            val title = element.select("h3").text()
            val imageUrl = element.select("img").attr("src")
            val description = element.select("p").text()
            val newsUrl = element.select("a").attr("href")

            newsList.add(NewsItem(title, imageUrl, description, newsUrl))
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

suspend fun loadImageBitmap(imageUrl: String): ImageBitmap? = withContext(Dispatchers.IO) {
    try {
        if (imageUrl.isNotEmpty()) {
            val bitmap = Picasso.get().load(imageUrl).get()
            return@withContext bitmap.asImageBitmap()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return@withContext null
}


//data class NewsItem(
//    val title: String,
//    val imageUrl: String,
//    val description: String,
//    val url: String
//)
//
//@Composable
//fun NewsScreen() {
//    val newsList = remember { mutableStateListOf<NewsItem>() }
//    val isLoading = remember { mutableStateOf(true) }
//
//    LaunchedEffect(Unit) {
//        isLoading.value = true
//        withContext(Dispatchers.IO) {
//            fetchNewsFromWebsite(newsList)
//        }
//        isLoading.value = false
//    }
//
//    Box(modifier = Modifier.fillMaxSize()) {
//
//        if (isLoading.value) {
//            CircularProgressIndicator(
//                modifier = Modifier.align(Alignment.Center),
//                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
//            )
//        } else {
//            NewsList(newsList = newsList)
//        }
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 20.dp, start = 20.dp)
//                .statusBarsPadding(),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            androidx.compose.material3.Text(
//                text = "News",
//                style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
//                fontSize = 20.sp,
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.align(Alignment.CenterVertically)
//            )
//            Spacer(modifier = Modifier.weight(2f))
//            Icon(
//                painter = painterResource(id = R.drawable.baseline_filter_list_24),
//                contentDescription = "",
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//                    .padding(end = 12.dp)
//
//            )
//        }
//
//
//    }
//}
//
//@Composable
//fun NewsList(newsList: List<NewsItem>) {
//    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
//        itemsIndexed(newsList) { index, item ->
//            NewsItemCard(item)
//        }
//    }
//}
//
//@Composable
//fun NewsItemCard(newsItem: NewsItem) {
//    Column(
//
//    ) {
//        val image: Painter =
//            rememberImagePainter(data = newsItem.url)
//        Image(
//            modifier = Modifier
//                .height(180.dp)
//                .fillMaxWidth()
//                .clip(RoundedCornerShape(14.dp))
//                .padding(start = 12.dp, end = 12.dp),
//            painter = image,
//            contentDescription = "",
//            contentScale = ContentScale.Crop
//        )
//
//        Text(
//            text = newsItem.title,
//            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
//            modifier = Modifier.padding(vertical = 8.dp),
//            color = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary,
//            fontSize = 16.sp
//        )
//    }
//}
//
//
//fun fetchNewsFromWebsite(newsList: MutableList<NewsItem>) {
//    try {
//        val url = "https://www.goal.com/en-ke"
//        val document: Document = Jsoup.connect(url).get()
//
//        val articleElements: Elements = document.select("article")
//        val maxNewsCount = minOf(articleElements.size, 10)
//
//        for (i in 0 until maxNewsCount) {
//            val element: Element = articleElements[i]
//            val title = element.select("h3").text()
//            val imageUrl = element.select("img").attr("src")
//            val description = element.select("p").text()
//            val newsUrl = element.select("a").attr("href")
//
//            newsList.add(NewsItem(title, imageUrl, description, newsUrl))
//        }
//    } catch (e: IOException) {
//        e.printStackTrace()
//    }
//}