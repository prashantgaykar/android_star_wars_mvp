package io.prashant.starwars.presenter

import io.prashant.starwars.contract.CharactersContract
import io.prashant.starwars.data.repository.CharacterRepository
import javax.inject.Inject

class CharactersPresenter @Inject constructor(
    private val view: CharactersContract.View,
    private val repository: CharacterRepository
) : CharactersContract.Presenter {

    override fun fetchCharacters() {
        view.showCharacters(
            repository.getCharacters()
        )
    }

}