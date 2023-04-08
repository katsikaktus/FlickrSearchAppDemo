package com.giota.flickrsearchdemoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.ui.screens.HomeScreen


@Composable
fun FlickrSearchApp(
    flickrSearchViewModel: FlickrSearchViewModel,
    modifier: Modifier = Modifier) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                flickrSearchUiState = flickrSearchViewModel.flickrSearchUiState
            )

        }
    }
}