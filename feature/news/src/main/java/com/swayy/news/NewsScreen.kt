package com.swayy.news

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
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
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



data class NewsItem(val title: String, val imageUrl: String, val description: String, val url: String)

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

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        // Handle the result here if needed
    }

    Column {
        TopAppBar(
            title = { Text(text = "News") },
            backgroundColor = MaterialTheme.colors.primary,
            contentColor = Color.White
        )
        Spacer(modifier = Modifier.height(8.dp))
        if (isLoading.value) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = MaterialTheme.colors.primary
            )
        } else {
            NewsList(newsList, launcher)
        }
    }
}

@Composable
fun NewsList(newsList: List<NewsItem>, launcher: ActivityResultLauncher<Intent>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        itemsIndexed(newsList) { index, item ->
            NewsItemCard(item, launcher)
        }
    }
}

@Composable
fun NewsItemCard(newsItem: NewsItem, launcher: ActivityResultLauncher<Intent>) {
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
                modifier = Modifier.height(200.dp)
            )
        }

        Text(
            text = newsItem.title,
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        Text(text = newsItem.description)
    }
}


fun fetchNewsFromWebsite(newsList: MutableList<NewsItem>) {
    try {
        val url = "https://www.goal.com/en-ke"
        val document: Document = Jsoup.connect(url).get()

        val articleElements: Elements = document.select("article")
        val maxNewsCount = minOf(articleElements.size, 10)

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