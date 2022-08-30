package io.prashant.starwars.util

import android.util.Log
import io.prashant.starwars.BuildConfig


object AppLogger {

    fun e(tag: String, s: String, throwable: Throwable? = null) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, s, throwable)
        }
    }

}
