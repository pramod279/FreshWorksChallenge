package com.freshworks.challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 *
 * [GiphyData] represents when a user adds a GIF to their [GifInfo] collection.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(tableName = "favourites_table")
data class GiphyData(
    val data: List<GifInfo>,
    val pagination: Pagination?
)

data class GifInfo(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "isFavourite") val isFavourite: Boolean?,
    @ColumnInfo(name = "title") val title: String?,
    val images: GifImages
)

data class GifImages(
    val fixed_height: GifImage
)

data class GifImage(
    @ColumnInfo(name = "url") val url: String?
)

data class Pagination(
    val count: Int?,
    val offset: Int?,
    val total_count: Int?
)