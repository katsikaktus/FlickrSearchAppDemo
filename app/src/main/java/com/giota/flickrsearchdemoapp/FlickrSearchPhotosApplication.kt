package com.giota.flickrsearchdemoapp

import android.app.Application
import com.giota.flickrsearchdemoapp.data.AppContainer
import com.giota.flickrsearchdemoapp.data.DefaultAppContainer

class FlickrSearchPhotosApplication : Application() {
    /** connect the application object to the application container*/
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = DefaultAppContainer()
    }
}