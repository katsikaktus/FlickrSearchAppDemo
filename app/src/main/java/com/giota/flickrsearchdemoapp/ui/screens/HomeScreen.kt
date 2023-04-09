package com.giota.flickrsearchdemoapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto

@Composable
fun HomeScreen(
    flickrSearchUiState: FlickrSearchUiState,
    modifier: Modifier = Modifier
){
    when(flickrSearchUiState){
        is FlickrSearchUiState.Loading -> LoadingScreen(modifier)
        is FlickrSearchUiState.Success -> PhotosGridScreen(flickrSearchUiState.photos.photo, modifier)
        is FlickrSearchUiState.Error -> ErrorScreen(modifier)
        else -> {}
    }

}
@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {


    }
}

@Composable
fun ErrorScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {

    }
}

private fun getImageUrl(photo_server: String, photo_id: String, photo_secret: String): String {
    return "https://live.staticflickr.com/${photo_server}/${photo_id}_${photo_secret}.jpg"
}

@Composable
fun FlickrSearchPhotoCard(photo: FlickrSearchPhoto, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(getImageUrl(photo.server, photo.id, photo.secret))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
    }

}

@Composable
fun PhotosGridScreen(photos: List<FlickrSearchPhoto>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { photo -> photo.id }) {
                photo -> FlickrSearchPhotoCard(photo)
        }
    }
}
