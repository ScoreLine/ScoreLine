package com.swayy.news.presentation.components

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.swayy.core.R

@Composable
fun NewsItemCard(imageUrl: String, title: String, navigateNewsDetails: (String) -> Unit,data:String) {
    fun convertToEncodedString(input: String): String {
        val encodedString = input.replace("/", "_SLASH_")
        return encodedString
    }
    Log.e("hii",data)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 12.dp, end = 12.dp)
            .clickable(onClick = {
                navigateNewsDetails(convertToEncodedString(data))
            })
    ) {

        val painter = rememberAsyncImagePainter(
            ImageRequest.Builder(LocalContext.current)
                .data(data = imageUrl)
                .apply(block = fun ImageRequest.Builder.() {
                    crossfade(true).placeholder(R.drawable.tes)
                }).build()
        )

        Image(
            painter = painter,
            contentDescription = "no image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
                .padding(start = 4.dp, end = 4.dp)
                .clip(RoundedCornerShape(14.dp))

        )

        Text(
            text = title,
            modifier = Modifier.padding(start = 8.dp, top = 12.dp, bottom = 12.dp),
            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold

        )
    }
}