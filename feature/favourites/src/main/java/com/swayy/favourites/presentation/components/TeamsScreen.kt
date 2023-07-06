package com.swayy.favourites.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.swayy.shared.presentation.state.ClubsListState

@Composable
fun TeamsScreen(
    state:ClubsListState
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.inverseOnSurface)
    ) {
        Text(
            text = "Top premier league teams",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(12.dp)
        )

        LazyColumn() {
            items(state.clubs) { club ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                        .height(56.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onPrimary)
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        AsyncImage(
                            model = ImageRequest.Builder(LocalContext.current)
                                .data(club.imageUrl)
                                .decoderFactory(SvgDecoder.Factory())
                                .build(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(8.dp)
                                .align(Alignment.CenterVertically),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = club.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.align(Alignment.CenterVertically)

                        )

                        Spacer(modifier = Modifier.weight(2f))
                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.inverseOnSurface)
                                .align(Alignment.CenterVertically)
                                .padding(2.dp)
                        ) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Follow",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Normal,
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .padding(4.dp)

                            )
                            Spacer(modifier = Modifier.width(8.dp))

                        }
                        Spacer(modifier = Modifier.width(20.dp))
                    }
                }

            }

        }

    }
}