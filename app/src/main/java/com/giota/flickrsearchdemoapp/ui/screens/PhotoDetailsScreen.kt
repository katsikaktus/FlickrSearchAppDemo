package com.giota.flickrsearchdemoapp.ui.screens

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.giota.flickrsearchdemoapp.network.Photo


@Composable
fun PhotoDetailsScreen(
    photo: Photo,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        onBackPressed()
    }
    Log.d("photo_details", photo.dateuploaded)
    PhotoDetails(
        photo.dateuploaded,
        photo.owner.username,
        modifier = modifier)

}

@Composable
private fun PhotoDetails (
    date: String,
    owner: String,
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ){
        Text(date)
        Text(owner)
    }
}
