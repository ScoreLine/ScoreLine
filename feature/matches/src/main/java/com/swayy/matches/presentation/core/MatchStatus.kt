package com.swayy.matches.presentation.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MatchStatus(
    text: String,
    color: Color
) {
    Box(
        modifier = Modifier
            .padding(top = 12.dp)
            .clip(RoundedCornerShape(100.dp))
            .size(28.dp)
            .background(color)
    ) {

        androidx.compose.material.Text(
            text = text,
            fontSize = 14.sp,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.align(Alignment.Center),
            color = MaterialTheme.colorScheme.onPrimary,
        )
    }
}

