package com.giota.flickrsearchdemoapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto



@Composable
fun PhotoDetailsScreen(
    photo: FlickrSearchPhoto,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        onBackPressed()
    }

    Column (modifier = modifier.fillMaxSize()){
        PhotoDetails(title = photo.title, owner = photo.owner, modifier = modifier)

    }
}

@Composable
private fun PhotoDetails (
    title: String,
    owner: String,
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        Text(text = title)
        Text(text = owner)
    }
}
