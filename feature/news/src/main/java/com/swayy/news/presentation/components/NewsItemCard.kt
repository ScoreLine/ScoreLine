package com.swayy.news.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun NewsItemCard(imageUrl: String, title: String) {
    Column (modifier = Modifier.fillMaxWidth().padding(start = 12.dp, end = 12.dp)){

        val image: Painter = rememberImagePainter(
            data = imageUrl,
//            builder = {
//                bitmapConfig(Bitmap.Config.RGB_565) //  RGB_565 for lower quality//faster loading
//            }
        )

        Image(
            painter = image,
            contentDescription = "no image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(180.dp)
                .fillMaxWidth()
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