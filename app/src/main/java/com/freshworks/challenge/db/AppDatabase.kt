package com.freshworks.challenge.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.utilities.Constants.DATABASE_NAME

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 *
 * The Room database for FreshWorks Challenge App
 */
@Database(entities = [GifInfo::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavouritesDao(): FavouritesDao

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