package io.prashant.starwars.util

import android.view.View
import android.view.Window
import android.view.WindowManager
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val dateToStringFormatter = SimpleDateFormat(
        "EEE, d MMM yyyy HH:mm:ss",
        Locale.getDefault()
    )

    //2014-12-20T21:17:50.496000Z
    private val stringToDateFormatter = SimpleDateFormat(
        "yyyy-MM-dd'T'HH:mm:ss.SSS",
        Locale.getDefault()
    )

    fun stringToDate(dateStr: String): Date? {
        return try {
            stringToDateFormatter.parse(dateStr)
        } catch (e: Exception) {
            null
        }
    }

    fun dateToString(date: Date?): String {
        return try {
            if (date != null) {
                dateToStringFormatter.format(date)
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }


    fun enableScreenCutoutDisplay(window: Window) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
            window.attributes.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
        }
    }

    fun enableFullScreen(window: Window) {
        val uiOptions =
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or //Prevent layout resize
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or       //when bars are shown
                    View.SYSTEM_UI_FLAG_FULLSCREEN or//Enable full screen
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or//Hides status bar
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or//Hides navigation bar
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY; //Immersive ui experience

        window.decorView.systemUiVisibility = uiOptions
    }
}