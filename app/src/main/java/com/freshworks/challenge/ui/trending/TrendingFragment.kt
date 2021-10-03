package com.freshworks.challenge.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.challenge.R
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.databinding.FragmentTrendingBinding
import com.freshworks.challenge.ui.common.LoaderStateAdapter
import com.freshworks.challenge.ui.trending.adapters.GifImageAdapter
import com.freshworks.challenge.ui.trending.adapters.GiphyViewHolder.FavouritesClickListener
import com.freshworks.challenge.utilities.Constants.GRID_COLUMNS
import com.freshworks.challenge.utilities.Constants.LIST_COLUMN
import com.freshworks.challenge.utilities.Constants.PAGE_OFFSET
import com.freshworks.challenge.utilities.snapToPosition
import com.freshworks.challenge.viewmodel.GiphyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * Tab 1 ==> Display all the Trending Gif Images
 */
@AndroidEntryPoint
class TrendingFragment : Fragment() {
    private lateinit var binding: FragmentTrendingBinding
    private val giphyViewModel: GiphyViewModel by viewModels()

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: GifImageAdapter

    /*Search Gif Query*/
    private var searchGifs: String = String()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        /*Initialise Gif Recycler View*/
        initGifRecyclerView()
        /*Search for Gifs Using Search View*/
        gifSearcher()
        /*Fetch Trending Gif Images*/
        fetchGifImages()
        /*Swipe Refresh Reload Action*/
        swipeRefreshGifs()
        /*Track Data Flow Progress States*/
        trackProgressStates()
    }

    /*Function for Initialising Gif Recycler View*/
    private fun initGifRecyclerView() {
        layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        binding.rvGiphy.layoutManager = layoutManager
        /*Gif Images Adapter For Displaying Gif Images*/
        adapter = GifImageAdapter(layoutManager, FavouritesClickListener {
            onFavouriteClicked(it)
        })
        /*Loader Adapter For Indicating Error & Retry Events*/
        binding.rvGiphy.adapter = adapter.withLoadStateFooter(
            footer = LoaderStateAdapter { adapter.retry() }
        )
    }

    /*Search For Gif Images*/
    private fun gifSearcher() {
        binding.svGifs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchQuery: String): Boolean {
                binding.svGifs.clearFocus()
                searchGifs = searchQuery
                reloadGifData()
                return false
            }

            override fun onQueryTextChange(searchQuery: String): Boolean {
                searchGifs = searchQuery
                reloadGifData()
                return false
            }
        })
    }

    /*Pull To Refresh For Refreshing Gif Data*/
    private fun swipeRefreshGifs() {
        binding.swipeRefresh.setOnRefreshListener {
            binding.svGifs.setQuery(String(), false)
            binding.svGifs.clearFocus()
            reloadGifData()
        }
    }

    /*Function for Fetching Fresh Gif Info*/
    private fun reloadGifData() {
        PAGE_OFFSET = 0
        fetchGifImages()
        giphyLayoutManager()
    }

    /*Reload Layout Manager When Search Action Performed*/
    private fun giphyLayoutManager() {
        if (searchGifs.isEmpty()) layoutManager.spanCount =
            GRID_COLUMNS else layoutManager.spanCount = LIST_COLUMN
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        binding.rvGiphy.snapToPosition(0)
    }

    /*Function for Fetching Trending Gif Images or Search Gif Images If Search Query Present*/
    private fun fetchGifImages() {
        lifecycleScope.launch {
            giphyViewModel.fetchGifImages(searchGifs).collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(gifInfo: GifInfo) {
        lifecycleScope.launch {
            giphyViewModel.toggleFavourites(gifInfo)
        }
    }

    /*Function for Tracking The Progress States*/
    private fun trackProgressStates() {
        lifecycleScope.launch {
            adapter.addLoadStateListener { loadState ->
                // Show loading spinner during initial load or refresh
                binding.swipeRefresh.isRefreshing = loadState.refresh is LoadState.Loading
                // Only show the list if refresh succeeds
                binding.rvGiphy.isVisible = loadState.refresh is LoadState.NotLoading
                // Show the error state if initial load or refresh fails
                binding.tvError.isVisible = loadState.refresh is LoadState.Error
                binding.tvError.text = getString(R.string.label_error)
                // Display Empty View For No Records Found*/
                val isEmptyList =
                    loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
                if (isEmptyList) showEmptyState()
            }
        }
    }

    /*Function for Displaying Empty View*/
    private fun showEmptyState() {
        binding.tvError.isVisible = true
        binding.tvError.text = getString(R.string.no_records_found)
    }
}