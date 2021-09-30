package com.freshworks.challenge.repository.local

import com.freshworks.challenge.data.db.AppDatabase

/**
 * @Author: Pramod Selvaraj
 * @Date: 30.09.2021
 *
 * Database Injector Class For Initialising Room Database For Local Storage
 */
object DatabaseInjector {
    var appDatabase: AppDatabase? = null

    fun injectDb(): AppDatabase? {
        return appDatabase
    }
}