package com.freshworks.challenge.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 * [GiphyData] represents when a GiphyDataResponse for the API Call
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
data class GiphyData(
    val data: List<GifInfo>,
    val pagination: Pagination?
)

data class Pagination(
    val count: Int?,
    val offset: Int?,
    val total_count: Int?
)

@Entity(tableName = "favourites_table")
data class GifInfo(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String?,
    @Embedded(prefix = "gif_") val images: GifImages,
    var isFavourite: Boolean
)

data class GifImages(
    @Embedded(prefix = "image_") val fixed_height: GifImage
)

data class GifImage(
    @ColumnInfo(name = "url") val url: String
)