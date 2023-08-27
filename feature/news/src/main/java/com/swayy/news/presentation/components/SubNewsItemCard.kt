package com.swayy.news.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import com.swayy.core.R

@Composable
fun SubNewsItemCard(imageUrl: String, title: String,navigateNewsDetails: (String) -> Unit,data:String){
    fun convertToEncodedString(input: String): String {
        val encodedString = input.replace("/", "_SLASH_")
        return encodedString
    }
    Row (modifier = Modifier
        .padding(start = 12.dp, end = 12.dp, top = 6.dp, bottom = 6.dp)
        .height(90.dp)
        .clickable(onClick = {
            navigateNewsDetails(convertToEncodedString(data))
        })){

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
                .height(80.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(8.dp))

        )

        Column (modifier = Modifier
            .padding(start = 12.dp, end = 12.dp, top = 10.dp, bottom = 10.dp)
            .align(Alignment.CenterVertically), verticalArrangement = Arrangement.SpaceBetween){
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                fontSize = 13.sp,
                maxLines = 2

            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Goal News",
                style = MaterialTheme.typography.bodySmall,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                fontSize = 12.sp

            )
        }

    }

}