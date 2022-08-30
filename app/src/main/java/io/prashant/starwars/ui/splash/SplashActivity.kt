package io.prashant.starwars.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import io.prashant.starwars.R
import io.prashant.starwars.ui.characters.CharacterActivity
import io.prashant.starwars.util.Utils
import kotlinx.coroutines.delay

class SplashActivity : AppCompatActivity() {

    companion object {
        private const val NAVIGATION_DELAY = 1500L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.enableScreenCutoutDisplay(this.window)
        Utils.enableFullScreen(this.window)
        setContentView(R.layout.activity_splash)
        navigateToNextScreen()
    }

    private fun navigateToNextScreen() {
        lifecycleScope.launchWhenCreated {
            delay(NAVIGATION_DELAY)
            startStarWarCharactersActivity()
        }
    }

    private fun startStarWarCharactersActivity() {
        val intent = Intent(this, CharacterActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finishAffinity()
    }
}