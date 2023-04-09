package com.giota.flickrsearchdemoapp.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FlickrSearchViewModel,
    ){
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val searchTag = viewModel.userInput

        SearchField(
            value = searchTag,
            onUserInputChanged = {viewModel.updateUserSearch(it)},
            onKeyboardSearch = { viewModel.getFlickrPhotos(searchTag) },
            onSearchButtonClicked = { viewModel.getFlickrPhotos(searchTag) },
        )
        Spacer(Modifier.height(8.dp))
        when(viewModel.flickrSearchUiState){
            is FlickrSearchUiState.Loading -> LoadingScreen(modifier)
            is FlickrSearchUiState.Success -> PhotosGridScreen((viewModel.flickrSearchUiState as FlickrSearchUiState.Success).photos.photo, modifier)
            is FlickrSearchUiState.Error -> ErrorScreen(modifier)
            else -> {}
        }
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
                .data(photo.imgUrl)
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

@Composable
fun SearchField(
    value: String,
    onUserInputChanged: (String) -> Unit,
    onKeyboardSearch: () -> Unit,
    onSearchButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.fillMaxWidth()){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .fillMaxWidth()
        )
        {
            TextField(
                value = value,
                singleLine = true,
                onValueChange = onUserInputChanged,
                label = { Text("Search...")},
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onKeyboardSearch()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(4.dp)

            )
            Card (
                modifier = modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .aspectRatio(1f),
                elevation = 8.dp,
            ){
                IconButton(
                    onClick = {
                        onSearchButtonClicked()
                        focusManager.clearFocus() },
                    modifier = Modifier
                        .size(48.dp)
                        .background(Color.Magenta)
                        .align(Alignment.CenterVertically)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search",
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }

            }


        }
    }


}

