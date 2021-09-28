package com.freshworks.challenge

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.freshworks.challenge.adapers.FAVOURITES_PAGE_INDEX
import com.freshworks.challenge.adapers.TRENDING_PAGE_INDEX
import com.freshworks.challenge.adapers.TabsPagerAdapter
import com.freshworks.challenge.databinding.FragmentViewPagerBinding
import com.google.android.material.tabs.TabLayoutMediator

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Dashboard Fragment ==> Initialise View Pager & Tabs Layout
 */
class DashboardFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentViewPagerBinding.inflate(inflater, container, false)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        val tabLayout = binding.tabs
        val viewPager = binding.viewPager

        /*Initialise Tabs Pager Adapter*/
        viewPager.adapter = TabsPagerAdapter(this)

        /*Attach the Icon & Title for Each Tab*/
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
            tab.setIcon(getTabIcon(position))
        }.attach()

        return binding.root
    }

    /*Function for Setting Tab Title*/
    private fun getTabTitle(position: Int): String {
        return when (position) {
            TRENDING_PAGE_INDEX -> getString(R.string.tab_title_trending)
            FAVOURITES_PAGE_INDEX -> getString(R.string.tab_title_favourites)
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