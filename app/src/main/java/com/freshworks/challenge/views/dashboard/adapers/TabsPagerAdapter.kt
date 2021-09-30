package com.freshworks.challenge.views.dashboard.adapers

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.freshworks.challenge.utilities.FAVOURITES_PAGE_INDEX
import com.freshworks.challenge.utilities.TRENDING_PAGE_INDEX
import com.freshworks.challenge.views.favourites.FavouritesFragment
import com.freshworks.challenge.views.trending.TrendingFragment

/**
 * @Author: Pramod Selvaraj
 * @Date: 27.09.2021
 *
 * Tabs Pager Adapter For Creating Tabs
 * Tab 1 ==> Trending Gifs
 * Tab 2 ==> Favourites Gifs
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