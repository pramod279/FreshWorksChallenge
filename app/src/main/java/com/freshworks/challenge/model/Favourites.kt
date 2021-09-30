package com.freshworks.challenge.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 * [Favourites] represents when a user adds a GIF to their [Favourites] collection.
 *
 * Declaring the column info allows for the renaming of variables without implementing a
 * database migration, as the column name would not change.
 */
@Entity(tableName = "favourites_table")
data class Favourites(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "url") val url: String?,
    @ColumnInfo(name = "isFavourite") val isFavourite: Boolean
)