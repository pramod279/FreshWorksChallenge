package com.freshworks.challenge.model.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
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
    @Query("SELECT * FROM favourites_table")
    fun getMyFavourites(): PagingSource<Int, Favourites>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(favourites: Favourites)

    @Query("DELETE FROM favourites_table WHERE id = :gifId")
    suspend fun removeFavourite(gifId: String)
}