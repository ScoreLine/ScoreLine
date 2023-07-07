package com.swayy.shared.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.swayy.core.R

@Composable
fun OnboardingScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.da), modifier = Modifier
                .fillMaxSize(), contentDescription = null,
            contentScale = ContentScale.Crop
        )

        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.two), modifier = Modifier
                    .size(250.dp)
                    .padding(top = 66.dp)
                    .align(Alignment.CenterHorizontally), contentDescription = null,
                contentScale = ContentScale.Crop,

            )
            Spacer(modifier = Modifier.weight(3f))

            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp), modifier = Modifier
                    .padding(start = 28.dp, end = 28.dp, bottom = 120
                        .dp)
                    .align(
                        Alignment.CenterHorizontally
                    )
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(
                        RoundedCornerShape(0.dp)
                    )
            ) {
                androidx.compose.material3.Text(
                    text = "Get Started",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(2.dp)

                )
            }
        }

    }
}