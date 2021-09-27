package com.freshworks.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.freshworks.challenge.databinding.ActivityDashboardBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Dashboard Activity ==> Initial Landing Screen
 */
@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityDashboardBinding>(this, R.layout.activity_dashboard)
    }
}