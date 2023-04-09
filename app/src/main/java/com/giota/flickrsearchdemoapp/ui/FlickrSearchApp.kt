package com.giota.flickrsearchdemoapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.ui.screens.FlickrSearchViewModel
import com.giota.flickrsearchdemoapp.ui.screens.HomeScreen


@Composable
fun FlickrSearchApp(
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
            val viewModel: FlickrSearchViewModel =
                viewModel(factory = FlickrSearchViewModel.Factory)
            HomeScreen(modifier, viewModel)
        }
    }
}