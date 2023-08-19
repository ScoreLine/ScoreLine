package com.swayy.matches.presentation.match_details.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.swayy.matches.presentation.h2h.HeadToHeadViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.swayy.matches.domain.model.HeadToHeadDomainModel


@Composable
fun HeadScreen(
    viewModel: HeadToHeadViewModel = hiltViewModel()
){
    val state = viewModel.h2h.value

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(state.h2h) { data ->
                H2HComponent(
                    h2h = data
                )
            }
        }
    }
}

@Composable
fun H2HComponent(h2h: HeadToHeadDomainModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            //.clip(androidx.compose.foundation.shape.CircleShape(10.dp))
                            .height(190.dp),
                        painter = rememberImagePainter(data = h2h.teams.home),
                        contentDescription = " image",
                        contentScale = ContentScale.Crop
                    )
                    //text to show name of the home team
                    Text("Column 1", fontSize = 20.sp)
                    Spacer(modifier = Modifier.height(1.dp))
                }
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text("Column 3", fontSize = 20.sp)
                // Add your content for the third column here
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .height(190.dp),
                        painter = rememberImagePainter(data = h2h.teams.away),
                        contentDescription = " image",
                        contentScale = ContentScale.Crop
                    )
                    //text to show name of the away team
                    Text("n.0 2", fontSize = 20.sp)

                    Spacer(modifier = Modifier.height(1.dp))

                }
                // Add your content for the first column here
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                //homescore text
                Text("n.0 2", fontSize = 20.sp)
                //awayscore text
                Text("n.0 2", fontSize = 20.sp)

            }
        }
    }
}
