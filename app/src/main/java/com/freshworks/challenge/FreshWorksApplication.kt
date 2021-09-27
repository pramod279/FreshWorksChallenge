package com.freshworks.challenge

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * FreshWorks Challenge Application Class
 */
@HiltAndroidApp
class FreshWorksApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}