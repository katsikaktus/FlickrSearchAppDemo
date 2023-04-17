package com.giota.flickrsearchdemoapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.lifecycle.viewmodel.compose.viewModel
import com.giota.flickrsearchdemoapp.ui.FlickrSearchApp
import com.giota.flickrsearchdemoapp.ui.screens.FlickrSearchViewModel
import com.giota.flickrsearchdemoapp.ui.theme.FlickrSearchDemoAppTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchDemoAppTheme {

                FlickrSearchApp()
            }
        }
    }
}
