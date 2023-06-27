package com.swayy.matches.presentation.match_details.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.swayy.matches.R
import com.swayy.matches.presentation.MatchViewmodel
import com.swayy.matches.presentation.events.EventsViewModel

@Composable
fun FactsScreen(
    id: Int,
    viewModel: EventsViewModel = hiltViewModel(),
    home: Int,
    away: Int
) {
//    LaunchedEffect(key1 = true){
//        viewModel.getEvents(fixture = id)
//    }

    val state = viewModel.matches.value

    androidx.compose.material3.Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        shape = RoundedCornerShape(6.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = androidx.compose.material3.MaterialTheme.colorScheme.onPrimary)
    ) {
        LazyColumn() {

            items(state.events) { match ->

                if (match.team?.id == home) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.Start)) {

                            if (match.type == "Goal") {
                                Row(modifier = Modifier.padding(start = 16.dp, top = 8.dp)) {
                                    Text(
                                        text = match.time?.elapsed.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        fontSize = 15.sp,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.width(10.dp))

                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_sports_baseball_24),
                                        modifier = Modifier
                                            .height(38.dp)
                                            .width(38.dp)
                                            .padding(10.dp)
                                            .align(Alignment.CenterVertically),
                                        contentDescription = "",

                                        )

                                    Spacer(modifier = Modifier.width(4.dp))

                                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        if (match.assist?.name != null) {
                                            Text(
                                                text = "Assist by " + (match.assist.name ?: ""),
                                                fontSize = 12.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }

                                }
                            }
                            if (match.type == "subst") {
                                Row(modifier = Modifier.padding(start = 16.dp, top = 14.dp)) {
                                    Text(
                                        text = match.time?.elapsed.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        fontSize = 15.sp,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                    )

                                    Spacer(modifier = Modifier.width(18.dp))

                                    Column {
                                        Card(
                                            shape = RoundedCornerShape(100.dp),
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier
                                                        .size(14.dp)
                                                        .background(MaterialTheme.colorScheme.primary)
                                                ) {
                                                    Image(
                                                        imageVector = Icons.Default.ArrowForward,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(2.dp)
                                                            .align(Alignment.CenterVertically),
                                                        contentDescription = "",
                                                        colorFilter = ColorFilter.tint(Color.White)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(1.dp))

                                        Card(
                                            shape = RoundedCornerShape(100.dp),
                                            modifier = Modifier
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier
                                                        .size(14.dp)
                                                        .background(Color.Red)
                                                ) {
                                                    Image(
                                                        imageVector = Icons.Default.ArrowBack,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(2.dp)
                                                            .align(Alignment.CenterVertically),
                                                        contentDescription = "",
                                                        colorFilter = ColorFilter.tint(Color.White)
                                                    )
                                                }
                                            }
                                        }

                                    }
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Column {
                                        Spacer(modifier = Modifier.height(3.dp))
                                        match.assist?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(1.dp))

                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }

                            if (match.type == "Card") {
                                if (match.detail == "Yellow Card") {
                                    Row(
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            top = 14.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        Text(
                                            text = match.time?.elapsed.toString(),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            fontSize = 15.sp,
                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                        )

                                        Spacer(modifier = Modifier.width(18.dp))

                                        Card(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .height(16.dp)
                                                .width(12.dp)
                                                .background(
                                                    Color.Yellow
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Yellow)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(20.dp))

                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                modifier = Modifier.align(CenterVertically),
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.padding(
                                            start = 16.dp,
                                            top = 12.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        Text(
                                            text = match.time?.elapsed.toString(),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            fontSize = 15.sp,
                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                        )

                                        Spacer(modifier = Modifier.width(18.dp))

                                        Card(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .height(16.dp)
                                                .width(12.dp)
                                                .background(
                                                    Color.Yellow
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(MaterialTheme.colorScheme.error)
                                            )
                                        }

                                        Spacer(modifier = Modifier.width(20.dp))

                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                modifier = Modifier.align(CenterVertically),
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
                if (match.team?.id == away) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Column(modifier = Modifier.align(Alignment.End)) {

                            if (match.type == "Goal") {
                                Row(modifier = Modifier.padding(end = 16.dp, top = 14.dp)) {

                                    Column(modifier = Modifier.align(Alignment.CenterVertically)) {
                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                        if (match.assist?.name != null) {
                                            Text(
                                                text = "Assist by " + (match.assist.name ?: ""),
                                                fontSize = 12.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(4.dp))
                                    Image(
                                        painter = painterResource(id = R.drawable.baseline_sports_baseball_24),
                                        modifier = Modifier
                                            .height(38.dp)
                                            .width(38.dp)
                                            .padding(10.dp)
                                            .align(Alignment.CenterVertically),
                                        contentDescription = "",

                                        )
                                    Spacer(modifier = Modifier.width(10.dp))

                                    Text(
                                        text = match.time?.elapsed.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        fontSize = 15.sp,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                    )


                                }
                            }
                            if (match.type == "subst") {
                                Row(modifier = Modifier.padding(end = 16.dp, top = 14.dp)) {

                                    Column {
                                        Spacer(modifier = Modifier.height(3.dp))
                                        match.assist?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(1.dp))

                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.width(20.dp))


                                    Column {
                                        Card(
                                            shape = RoundedCornerShape(100.dp),
                                            modifier = Modifier.padding(top = 4.dp)
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier
                                                        .size(14.dp)
                                                        .background(MaterialTheme.colorScheme.primary)
                                                ) {
                                                    Image(
                                                        imageVector = Icons.Default.ArrowForward,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(2.dp)
                                                            .align(Alignment.CenterVertically),
                                                        contentDescription = "",
                                                        colorFilter = ColorFilter.tint(Color.White)
                                                    )
                                                }
                                            }
                                        }

                                        Spacer(modifier = Modifier.height(1.dp))

                                        Card(
                                            shape = RoundedCornerShape(100.dp),
                                            modifier = Modifier
                                        ) {
                                            Column {
                                                Row(
                                                    modifier = Modifier
                                                        .size(14.dp)
                                                        .background(Color.Red)
                                                ) {
                                                    Image(
                                                        imageVector = Icons.Default.ArrowBack,
                                                        modifier = Modifier
                                                            .fillMaxSize()
                                                            .padding(2.dp)
                                                            .align(Alignment.CenterVertically),
                                                        contentDescription = "",
                                                        colorFilter = ColorFilter.tint(Color.White)
                                                    )
                                                }
                                            }
                                        }

                                    }
                                    Spacer(modifier = Modifier.width(18.dp))

                                    Text(
                                        text = match.time?.elapsed.toString(),
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.align(Alignment.CenterVertically),
                                        fontSize = 15.sp,
                                        style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                        color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                    )

                                }
                            }

                            if (match.type == "Card") {
                                if (match.detail == "Yellow Card") {
                                    Row(
                                        modifier = Modifier.padding(
                                            end = 16.dp,
                                            top = 14.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                modifier = Modifier.align(CenterVertically),
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Card(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .height(16.dp)
                                                .width(12.dp)
                                                .background(
                                                    Color.Yellow
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(Color.Yellow)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(18.dp))

                                        Text(
                                            text = match.time?.elapsed.toString(),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            fontSize = 15.sp,
                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                        )


                                    }
                                } else {
                                    Row(
                                        modifier = Modifier.padding(
                                            end = 16.dp,
                                            top = 14.dp,
                                            bottom = 8.dp
                                        )
                                    ) {
                                        match.player?.let {
                                            Text(
                                                text = it.name,
                                                fontSize = 14.sp,
                                                modifier = Modifier.align(CenterVertically),
                                                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(20.dp))
                                        Card(
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .height(16.dp)
                                                .width(12.dp)
                                                .background(
                                                    Color.Yellow
                                                )
                                        ) {
                                            Box(
                                                modifier = Modifier
                                                    .fillMaxSize()
                                                    .background(MaterialTheme.colorScheme.error)
                                            )
                                        }
                                        Spacer(modifier = Modifier.width(18.dp))
                                        Text(
                                            text = match.time?.elapsed.toString(),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.align(Alignment.CenterVertically),
                                            fontSize = 15.sp,
                                            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                                            color = androidx.compose.material3.MaterialTheme.colorScheme.onSurface
                                        )

                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
    }

}