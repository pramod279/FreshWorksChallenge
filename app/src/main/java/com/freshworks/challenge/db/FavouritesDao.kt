package com.freshworks.challenge.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.freshworks.challenge.data.entities.GifInfo

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
    fun getMyFavourites(): PagingSource<Int, GifInfo>

    @Query("SELECT EXISTS (SELECT * FROM favourites_table WHERE id = :gifId)")
    suspend fun isFavourite(gifId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavourite(gifInfo: GifInfo)

    @Query("DELETE FROM favourites_table WHERE id = :gifId")
    suspend fun removeFavourite(gifId: String)
}