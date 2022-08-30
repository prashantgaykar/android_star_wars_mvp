package io.prashant.starwars.data.local

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    @Expose
    @SerializedName("name")
    val name: String,

    @Expose
    @SerializedName("height")
    val height: String,

    @Expose
    @SerializedName("mass")
    val mass: String,

    @Expose
    @SerializedName("created")
    val created: String,
) : Parcelable
