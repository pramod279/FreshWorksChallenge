package com.freshworks.challenge.model.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.freshworks.challenge.model.Favourites

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 *
 * The following code defines a DAO called FavouritesDao. FavouritesDao provides
 * the methods that the rest of the app uses to interact with data in the user table.
 */
@Dao
interface FavouritesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourites: Favourites)

    @Delete
    fun deleteFavourite(favourites: Favourites)
}