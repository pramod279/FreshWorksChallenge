package com.freshworks.challenge.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freshworks.challenge.R
import com.freshworks.challenge.databinding.FragmentDashboardBinding
import com.freshworks.challenge.ui.dashboard.adapers.TabsPagerAdapter
import com.freshworks.challenge.utilities.Constants.FAVOURITES_PAGE_INDEX
import com.freshworks.challenge.utilities.Constants.TRENDING_PAGE_INDEX
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Dashboard Fragment ==> Initialise View Pager & Tabs Layout
 */
@AndroidEntryPoint
class DashboardFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        /*Initialise Tabs Pager Adapter*/
        viewPager.adapter = TabsPagerAdapter(this)

        /*Attach the Icon & Title for Each Tab*/
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
            tab.setIcon(getTabIcon(position))
        }.attach()

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        return binding.root
    }

    /*Function for Setting Tab Title*/
    private fun getTabTitle(position: Int): String {
        return when (position) {
            TRENDING_PAGE_INDEX -> getString(R.string.tab_trending)
            FAVOURITES_PAGE_INDEX -> getString(R.string.tab_favourites)
            else -> throw IndexOutOfBoundsException()
        }
    }

    /*Function for Setting Tab Icon*/
    private fun getTabIcon(position: Int): Int {
        return when (position) {
            TRENDING_PAGE_INDEX -> R.drawable.tab_selector_trending
            FAVOURITES_PAGE_INDEX -> R.drawable.tab_selector_favourites
            else -> throw IndexOutOfBoundsException()
        }
    }
}