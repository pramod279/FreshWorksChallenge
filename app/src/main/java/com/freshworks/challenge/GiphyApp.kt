package com.freshworks.challenge

import android.app.Application
import com.freshworks.challenge.data.db.AppDatabase
import com.freshworks.challenge.repository.local.DatabaseInjector

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * FreshWorks Challenge Application Class
 */
class GiphyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initRoomDB()
    }

    /*Initialise Room Database*/
    private fun initRoomDB() {
        DatabaseInjector.appDatabase = AppDatabase.getDatabase(this@GiphyApp)
    }
}