package com.giota.flickrsearchdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.ui.FlickrSearchApp
import com.giota.flickrsearchdemoapp.ui.FlickrSearchViewModel
import com.giota.flickrsearchdemoapp.ui.theme.FlickrSearchDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchDemoAppTheme {
                val viewModel: FlickrSearchViewModel =
                    viewModel(factory = FlickrSearchViewModel.Factory)
                FlickrSearchApp(viewModel)
            }
        }
    }
}
