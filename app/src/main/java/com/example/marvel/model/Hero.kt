package com.example.marvel.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class HeroData(
    @SerializedName("data")
    val data: HeroResponse
)

data class HeroResponse(
    val results: List<Hero>
)

@Entity
data class Hero(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    val id: Long,

    @SerializedName("name")
    val name: String,

    @Embedded
    @SerializedName("thumbnail")
    val thumbnail: Thumbnail,

    @SerializedName("description")
    val description: String

)

data class Thumbnail(
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
) {
    fun concatThumbnail(): String {
        return "$path.$extension"
    }
}
