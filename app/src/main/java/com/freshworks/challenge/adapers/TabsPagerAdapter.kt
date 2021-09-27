package com.freshworks.challenge.adapers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.freshworks.challenge.FavouritesFragment
import com.freshworks.challenge.TrendingFragment

const val TRENDING_PAGE_INDEX = 0
const val FAVOURITES_PAGE_INDEX = 1

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Tabs Pager Adapter For Creating Tabs
 */
class TabsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    /**
     * Mapping of the ViewPager page indexes to their respective Fragments
     */
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        TRENDING_PAGE_INDEX to { TrendingFragment() },
        FAVOURITES_PAGE_INDEX to { FavouritesFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}