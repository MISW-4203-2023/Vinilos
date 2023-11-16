package com.team3.vinilos.view.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.team3.vinilos.R
import com.team3.vinilos.model.Datasource
import com.team3.vinilos.model.models.Artist
import com.team3.vinilos.viewModel.ArtistUiState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArtistScreen(state: ArtistUiState, retryAction: () -> Unit) {

    when (state) {
        is ArtistUiState.Loading -> Text(text = stringResource(R.string.loading_title))
        is ArtistUiState.Success -> ArtistDetail(artist = state.artist)
        is ArtistUiState.Error -> Column {
            Text(text = stringResource(R.string.error_title))
            Button(onClick = retryAction) {
                Text(text = stringResource(R.string.error_retry))
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArtistDetail(artist: Artist, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
    ){
        Text("Detalle Artista", fontSize = 24.sp , modifier = Modifier.padding(10.dp))
        OutlinedCard(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface,
            ),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    Text(
                        text = "Artista",
                        modifier = Modifier,
                        textAlign = TextAlign.Center,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Icon(imageVector = Icons.Filled.Star , contentDescription = "Favorito")
            }

            val dateSt = artist.birthDate.toString()
            val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val formattedDate = LocalDateTime.parse(dateSt, dateFormatter)
            val res = DateTimeFormatter.ofPattern("MMMM dd, yyyy").format(formattedDate) // August 04, 2017 | 6:39pm

            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current).data(artist.image)
                    .crossfade(true).build(),
                error = painterResource(R.drawable.logo),
                placeholder = painterResource(R.drawable.logo),
                contentDescription = "Imagen",
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .size(width = 400.dp, height = 300.dp)

            )
            Text(
                text = artist.name,
                modifier = Modifier
                    .padding(16.dp, bottom = 4.dp, top = 16.dp),
                fontSize = 16.sp,
            )
            Text(
                text = res,
                modifier = Modifier
                    .padding(start = 16.dp, bottom = 16.dp, top = 4.dp),
                fontSize = 8.sp,
            )

            Text(
                text = artist.description.toString(),
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                fontSize = 10.sp,
                lineHeight = 16.sp
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, end = 16.dp, bottom = 16.dp),
                horizontalArrangement = Arrangement.End,
            ) {
                OutlinedButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Star, contentDescription = "Favorito" )
                    Text(text = "Quitar")
                }
            }



        }

    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
private fun ArtistPreview() {
    ArtistDetail(Datasource().getArtist())
}




