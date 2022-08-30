package io.prashant.starwars.data.remote.characters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import io.prashant.starwars.data.local.Character
import io.prashant.starwars.data.remote.NetworkService

class RemoteCharactersPagingSource(
    private val defaultPageIndex: Int,
    private val networkService: NetworkService
) :
    PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: defaultPageIndex
        return try {
            val response = networkService.fetchCharacters(pageNo = page)
            val characters = response.results
            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.nextPageUrl == null) null else page + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}