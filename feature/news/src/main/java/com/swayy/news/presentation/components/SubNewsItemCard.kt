package com.swayy.news.presentation.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter

@Composable
fun SubNewsItemCard(imageUrl: String, title: String){

    Row (modifier = Modifier
        .padding(12.dp)
        .height(80.dp)){

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
                .height(80.dp)
                .width(140.dp)
                .clip(RoundedCornerShape(8.dp))

        )

        Column (modifier = Modifier
            .padding(12.dp)
            .align(Alignment.CenterVertically), verticalArrangement = Arrangement.SpaceBetween){
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface,
                fontSize = 15.sp,
                maxLines = 2

            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Goal News",
                style = MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary,
                fontSize = 13.sp

            )
        }

    }

}