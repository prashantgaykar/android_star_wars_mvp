package io.prashant.starwars.data.remote


import io.prashant.starwars.data.remote.characters.CharacterResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET(ApiConstants.PEOPLE)
    suspend fun fetchCharacters(
        @Query("page") pageNo: Int
    ): CharacterResponse

}