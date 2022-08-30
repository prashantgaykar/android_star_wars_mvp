package io.prashant.starwars.data.remote.characters

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import io.prashant.starwars.data.local.Character

data class CharacterResponse(
    @Expose
    @SerializedName("count")
    val count: Int,

    @Expose
    @SerializedName("next")
    val nextPageUrl: String?,

    @Expose
    @SerializedName("previous")
    val prevPageUrl: String?,

    @Expose
    @SerializedName("results")
    val results: List<Character>
)


