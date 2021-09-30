package com.freshworks.challenge.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freshworks.challenge.model.GiphyData
import com.freshworks.challenge.model.dao.FavouritesDao
import com.freshworks.challenge.utilities.DATABASE_NAME

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 *
 * The Room database for this app
 */
@Database(entities = [GiphyData::class], version = 1, exportSchema = false)
public abstract class AppDatabase : RoomDatabase() {

    abstract fun favouritesDao(): FavouritesDao

    companion object {
        // Singleton prevents multiple instances of database opening at the same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the INSTANCE is not null, then return it, if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java, DATABASE_NAME
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}