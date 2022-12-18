package com.example.marvel.model

import androidx.room.*
import com.google.firebase.database.annotations.NotNull
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class HeroData(
    @SerializedName("data")
    val data: HeroResponse
)

data class HeroResponse(
    val results: List<Hero>
)

@Entity(tableName = "HEROES")
data class Hero (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    @NotNull
    @SerializedName("id")
    val id: Long,

    @NotNull
    @SerializedName("name")
    val name: String,

//    @Embedded
//    @NotNull
//    @SerializedName("thumbnail")
//    val thumbnail: Thumbnail,

    @SerializedName("description")
    val description: String?
) : Serializable

data class Thumbnail (
    @SerializedName("path")
    val path: String,
    @SerializedName("extension")
    val extension: String,
){
    fun concatThumbnail(): String {
        return "$path.$extension"
    }
}
