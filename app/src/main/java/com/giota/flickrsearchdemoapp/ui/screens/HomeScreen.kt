package com.giota.flickrsearchdemoapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.giota.flickrsearchdemoapp.ui.FlickrSearchUiState

@Composable
fun HomeScreen(
    flickrSearchUiState: FlickrSearchUiState,
    modifier: Modifier = Modifier
){
    when(flickrSearchUiState){
        is FlickrSearchUiState.Loading -> LoadingScreen(modifier)
        is FlickrSearchUiState.Success -> ResultScreen(flickrSearchUiState.photos, modifier)
        is FlickrSearchUiState.Error -> ErrorScreen(modifier)
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

/**
 * The home screen displaying result of fetching photos.
 */
@Composable
fun ResultScreen(flickrSearchUiState: String, modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Text(flickrSearchUiState)
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
