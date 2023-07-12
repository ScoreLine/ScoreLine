package com.swayy.favourites.presentation.components

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.swayy.core.util.UiEvents
import com.swayy.shared.presentation.FavoriteViewModel
import com.swayy.shared.presentation.state.ClubsListState
import kotlinx.coroutines.flow.collectLatest
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.times
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette
import coil.Coil
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.request.SuccessResult
import com.swayy.core.R
import com.swayy.shared.domain.model.Favorite
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.apache.batik.transcoder.TranscoderInput
import org.apache.batik.transcoder.TranscoderOutput
import org.apache.batik.transcoder.image.PNGTranscoder
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TeamsScreen(
    state: ClubsListState,
    viewModel: FavoriteViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val favoritesUiState = viewModel.favoritesUiState.value

    LaunchedEffect(key1 = true, block = {

        viewModel.getFavorites()

        viewModel.eventsFlow.collectLatest { event ->
            when (event) {
                is UiEvents.SnackbarEvent -> {
                    snackbarHostState.showSnackbar(
                        message = event.message
                    )
                }

                else -> {}
            }
        }
    })

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState
            )
        }
    ) {
        it

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.inverseOnSurface)
        ) {

            LazyColumn() {
                item {

                    val itemHeight = 190.dp

                    val numColumns = 2 // Number of columns in the grid

                    val numRows = (favoritesUiState.favorites.size + numColumns - 1) / numColumns
                    val totalHeight = (numRows * itemHeight).coerceAtLeast(0.dp)

                    if (!favoritesUiState.isLoading) {
                        LazyVerticalGrid(columns = GridCells.Fixed(numColumns), modifier = Modifier.height(
                            totalHeight
                        ), content = {
                            items(
                                favoritesUiState.favorites,
                                key = { favorite -> favorite.name }
                            ) { favorite ->
                                TeamItem(
                                    modifier = Modifier.animateItemPlacement(),
                                    favorite = favorite,
                                )
                            }
                        })
                    }
                    Modifier.height(30.dp)
                    Text(
                        text = "Top premier league teams",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(12.dp)
                    )
                }


                items(state.clubs, key = { game -> game.name }) { club ->
                    val isFavorite =
                        viewModel.inOnlineFavorites(name = club.name).observeAsState().value != null

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
                                    .clickable(onClick = {
                                        if (isFavorite) {
                                            viewModel.deleteAnOnlineFavorite(name = club.name)
                                        } else {
                                            viewModel.insertAFavorite(
                                                name = club.name,
                                                imageUrl = club.imageUrl,
                                                websiteUrl = club.websiteUrl
                                            )
                                        }
                                    })
                            ) {
                                Spacer(modifier = Modifier.width(8.dp))

                                if (isFavorite) {
                                    Text(
                                        text = "Following",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)

                                    )
                                } else {
                                    Text(
                                        text = "Follow",
                                        style = MaterialTheme.typography.bodyMedium,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .align(Alignment.CenterVertically)
                                            .padding(4.dp)

                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                            }
                            Spacer(modifier = Modifier.width(20.dp))
                        }
                    }

                }

            }

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)
@Composable
fun TeamItem(modifier: Modifier, favorite: Favorite) {
    Card(
        onClick = { /*TODO*/ }, modifier = modifier
            .size(186.dp)
            .padding(12.dp), elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        val painterResource = when (favorite.name) {
            "Arsenal" -> R.drawable.arsenal
            "Aston Villa" -> R.drawable.astonvilla
            "AFC Bournemouth" -> R.drawable.bournemouth
            "Brentford" -> R.drawable.brentford
            "Brighton & Hove Albion" -> R.drawable.brighton
            "Burnley" -> R.drawable.burnley
            "Chelsea" -> R.drawable.chelsea
            "Crystal Palace" -> R.drawable.crystal
            "Everton" -> R.drawable.everton
            "Fulham" -> R.drawable.fulham
            "Liverpool" -> R.drawable.liverpool
            "Luton Town" -> R.drawable.luton
            "Manchester City" -> R.drawable.manchestercity
            "Nottingham Forest" -> R.drawable.forest
            "Sheffield United" -> R.drawable.sheffield
            "Tottenham Hotspur" -> R.drawable.tottenham
            "West Ham United" -> R.drawable.westham
            "Wolverhampton Wanderers" -> R.drawable.wolves
            "Newcastle United" -> R.drawable.newcastle
            "Manchester United" -> R.drawable.united
            else -> R.drawable.united
        }

        val context = LocalContext.current

        val dominantColor = extractDominantColor(context, painterResource)

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(dominantColor)
        ) {
            Column(modifier = modifier.fillMaxSize()) {

                Image(
                    painter = painterResource(id = painterResource), modifier = Modifier
                        .size(70.dp)
                        .padding(12.dp), contentDescription = null,
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = favorite.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    modifier = modifier.padding(12.dp),
                    color = Color.White

                )
            }
        }


    }
}


fun extractDominantColor(context: Context, resourceId: Int): Color {
    val bitmap = BitmapFactory.decodeResource(context.resources, resourceId)

    val palette = Palette.from(bitmap).generate()
    val dominantSwatch = palette.dominantSwatch ?: palette.vibrantSwatch ?: palette.mutedSwatch

    return dominantSwatch?.rgb?.let { Color(it) } ?: Color.White
}