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
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.giota.flickrsearchdemoapp.R
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
        val selectedPhoto = viewModel.selectedPhoto

        if (selectedPhoto != null) {
            PhotoDetailsScreen(
                photo = selectedPhoto,
                onBackPressed = { viewModel.clearSelectedPhoto() }
            )
        } else {
            SearchField(
                value = searchTag,
                onUserInputChanged = {viewModel.updateUserSearch(it)},
                onKeyboardSearch = { viewModel.getFlickrPhotos(searchTag) },
                onSearchButtonClicked = { viewModel.getFlickrPhotos(searchTag) },
            )
            Spacer(Modifier.height(8.dp))

            when(viewModel.flickrSearchUiState){
                is FlickrSearchUiState.Loading -> LoadingScreen(modifier)
                is FlickrSearchUiState.Success -> PhotosGridScreen(
                    (viewModel.flickrSearchUiState as FlickrSearchUiState.Success).photos.photo,
                    onPhotoClicked = { viewModel.selectPhoto(it) },
                    modifier)
                is FlickrSearchUiState.Error -> ErrorScreen((viewModel.flickrSearchUiState as FlickrSearchUiState.Error).errorMessage, modifier)
                is FlickrSearchUiState.NoRequest -> EmptyScreen(modifier)
            }
        }


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
            errorMessage,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}

@Composable
fun EmptyScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 32.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            stringResource(R.string.welcome_prompt),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FlickrSearchPhotoCard(
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
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current)
                .data(photo.imgUrl)
                .crossfade(true)
                .diskCachePolicy(CachePolicy.ENABLED)
                .build(),
            contentDescription = null,
            error = painterResource(R.drawable.ic_broken_image),
            placeholder = painterResource(R.drawable.loading_img),
            contentScale = ContentScale.Crop,
        )
    }

}

@Composable
fun PhotosGridScreen(
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

