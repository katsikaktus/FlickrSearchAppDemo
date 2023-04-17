package com.giota.flickrsearchdemoapp.ui.screens


import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.giota.flickrsearchdemoapp.R
import com.giota.flickrsearchdemoapp.network.FlickrSearchPhoto

@Composable
fun SearchScreen(
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
            onUserInputChanged = { viewModel.updateUserSearch(it) },
            onKeyboardSearch = { viewModel.getFlickrPhotos(searchTag) },
            onSearchButtonClicked = { viewModel.getFlickrPhotos(searchTag) },
        )
        Spacer(Modifier.height(8.dp))

        when(viewModel.flickrSearchUiState){
            is FlickrSearchUiState.NoRequest -> WelcomeScreen(modifier)
            is FlickrSearchUiState.Loading -> LoadingScreen(modifier)
            is FlickrSearchUiState.Success -> PhotosGridScreen(
                searchTag,
                (viewModel.flickrSearchUiState as FlickrSearchUiState.Success).photos.photo,
                onPhotoClicked = { viewModel.selectPhoto(it)},
                modifier)
            is FlickrSearchUiState.Error -> ErrorScreen(
                (viewModel.flickrSearchUiState as FlickrSearchUiState.Error).errorMessage,
                modifier)
        }
    }
}

@Composable
fun WelcomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.welcome_prompt),
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            modifier = Modifier.size(200.dp),
            painter = painterResource(R.drawable.loading_img),
            contentDescription = stringResource(R.string.loading)
        )

    }
}

@Composable
fun ErrorScreen(
    error: FlickrSearchError,
    modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val errorMessage = when (error) {
            FlickrSearchError.NO_INTERNET_CONNECTION -> stringResource(R.string.internet_error_prompt)
            FlickrSearchError.NO_RESULTS_FOUND -> stringResource(R.string.no_photos_found)
            FlickrSearchError.UNKNOWN_ERROR -> stringResource(R.string.unknown_error_try_later)
        }


        Text(
            text = errorMessage,
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}


@OptIn(ExperimentalMaterialApi::class, ExperimentalCoilApi::class)
@Composable
fun FlickrSearchPhotoCard(
    tag: String,
    photo: FlickrSearchPhoto,
    onClick: () -> Unit,
    modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(4.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp,
        onClick = onClick
    ) {

        val imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(photo.imgUrl)
            .diskCachePolicy(CachePolicy.ENABLED)
            .diskCacheKey(tag + "_" + photo.imgUrl)
            .tag(tag)
            .build()

        AsyncImage(
            model = imageRequest,
            contentDescription = photo.title,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
        )


    }

}

@Composable
fun PhotosGridScreen(
    tag: String,
    photos: List<FlickrSearchPhoto>,
    onPhotoClicked: (FlickrSearchPhoto) -> Unit,
    modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(150.dp),
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = photos, key = { photo -> photo.id }) { photo ->
            FlickrSearchPhotoCard(
                tag = tag,
                photo = photo,
                onClick = { onPhotoClicked(photo) })
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
                label = { Text(
                    text = stringResource(R.string.search_prompt),
                    style = MaterialTheme.typography.body1,
                )},
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
                        .background(MaterialTheme.colors.primary)
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

