package io.prashant.starwars.contract

import androidx.paging.PagingData
import io.prashant.starwars.data.local.Character
import kotlinx.coroutines.flow.Flow

interface CharactersContract {

    interface View {
        fun showCharacters(flowCharacters: Flow<PagingData<Character>>)
    }

    interface Presenter {
        fun fetchCharacters()
    }
}