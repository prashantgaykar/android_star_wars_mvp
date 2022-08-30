package io.prashant.starwars.data.repository

import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dagger.hilt.android.qualifiers.ApplicationContext
import io.prashant.starwars.data.local.Character
import io.prashant.starwars.data.remote.NetworkService
import io.prashant.starwars.data.remote.characters.RemoteCharactersPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepository @Inject constructor(
    @ApplicationContext val applicationContext: Context,
    private val networkService: NetworkService,
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    private fun getPageConfig() = PagingConfig(pageSize = DEFAULT_PAGE_SIZE)

    fun getCharacters(pagingConfig: PagingConfig = getPageConfig()): Flow<PagingData<Character>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = {
                RemoteCharactersPagingSource(
                    DEFAULT_PAGE_INDEX,
                    networkService
                )
            }
        ).flow
    }
}