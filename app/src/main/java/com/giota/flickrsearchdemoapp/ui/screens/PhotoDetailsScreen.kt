package com.giota.flickrsearchdemoapp.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.giota.flickrsearchdemoapp.R



@Composable
fun PhotoDetailsScreen(
    imgUrl: String,
    title: String,
    username: String,
    description: String,
    dateUploaded: String,
    views: Int,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit = {}
) {
    BackHandler {
        onBackPressed()
    }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    )
    {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onBackPressed,
                modifier = Modifier

                    .background(MaterialTheme.colors.surface, shape = CircleShape),
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = title, style = MaterialTheme.typography.h2)
        }
        FlickrSearchPhotoCard(
            imgUrl,
            modifier = modifier
        )
        Spacer(Modifier.height(8.dp))

        PhotoDetails(
            username,
            description,
            dateUploaded,
            views,
            modifier = modifier)
    }
}

@Composable
private fun PhotoDetails (
    username: String,
    description: String,
    dateUploaded: String,
    views: Int,
    modifier: Modifier
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)

    ){
        Card(
            modifier = Modifier,
            shape = MaterialTheme.shapes.small
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primaryVariant)
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                Row(modifier = modifier) {
                    Text(
                        text ="Username: ",
                        style = MaterialTheme.typography.h2)
                    Text(
                        text = username,
                        style = MaterialTheme.typography.body1)
                }
                Row(modifier = modifier) {
                    Text(
                        text = "Uploaded date: ",
                        style = MaterialTheme.typography.h2)
                    Text(text = dateUploaded,
                        style = MaterialTheme.typography.body1)

                }
                Row(modifier = modifier) {
                    Text(
                        text ="Views: ",
                        style = MaterialTheme.typography.h2)
                    Text(
                        text = views.toString(),
                        style = MaterialTheme.typography.body1)
                }

            }

        }

        Column(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp)
                .background(MaterialTheme.colors.background)
                .verticalScroll(rememberScrollState())) {
            Row(modifier = modifier) {
                Text(
                    text = "Description: ",
                    style = MaterialTheme.typography.h2)
                Text(
                    text = description,
                    style = MaterialTheme.typography.body1)
            }
        }

    }
}


@Composable
fun FlickrSearchPhotoCard(
    imgUrl: String,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1.2f),
        elevation = 2.dp,
    ) {

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imgUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .build()

        AsyncImage(
            model = imageRequest,
            contentDescription = null,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
        )


    }

}