package io.prashant.starwars

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.prashant.starwars.util.AppLogger

@HiltAndroidApp
class StarWarApplication : Application() {

    companion object {
        private const val TAG = "StarWarApplication"
    }

    override fun onCreate() {
        super.onCreate()
        AppLogger.e(TAG, "App Start....")
    }

}