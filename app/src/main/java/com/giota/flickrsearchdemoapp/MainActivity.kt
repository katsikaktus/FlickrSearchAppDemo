package com.giota.flickrsearchdemoapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.giota.flickrsearchdemoapp.ui.FlickrSearchApp
import com.giota.flickrsearchdemoapp.ui.theme.FlickrSearchDemoAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlickrSearchDemoAppTheme {
                FlickrSearchApp()
            }
        }
    }
}
