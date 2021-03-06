package com.freshworks.challenge

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import com.freshworks.challenge.databinding.ActivityGiphyBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Giphy Activity ==> A simple [GiphyActivity] subclass as the default destination in the navigation.
 */
@AndroidEntryPoint
class GiphyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityGiphyBinding>(this, R.layout.activity_giphy)
    }
}