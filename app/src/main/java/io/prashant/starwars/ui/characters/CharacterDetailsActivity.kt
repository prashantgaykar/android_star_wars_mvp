package io.prashant.starwars.ui.characters

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import io.prashant.starwars.R
import io.prashant.starwars.data.local.Character
import io.prashant.starwars.databinding.ActivityCharacterDetailsBinding
import io.prashant.starwars.util.Constants
import io.prashant.starwars.util.Utils

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityCharacterDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityCharacterDetailsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        setupToolbar()

        val character = intent?.getParcelableExtra<Character>(Constants.IntentKey.CHARACTER_DATA)
        if (character != null) {
            loadCharactersDetails(character)
        } else {
            Toast.makeText(this, R.string.character_details_not_found, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(viewBinding.appToolbar.appToolbar)
        supportActionBar?.title = getString(R.string.characters_details)
    }


    private fun loadCharactersDetails(character: Character) {
        with(viewBinding) {
            tvName.text = character.name
            tvHeight.text = character.height
            tvMass.text = character.mass
            val createdAtDate = Utils.stringToDate(character.created)
            tvCreationDateTime.text = Utils.dateToString(createdAtDate)
        }
    }


}