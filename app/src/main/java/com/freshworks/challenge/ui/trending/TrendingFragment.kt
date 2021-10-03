package com.freshworks.challenge.ui.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
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
import com.freshworks.challenge.utilities.shortToast
import com.freshworks.challenge.utilities.snapToPosition
import com.freshworks.challenge.viewmodel.GiphyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val giphyGifViewModel: GiphyViewModel by viewModels()

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: GifImageAdapter

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
        /*Fetch Trending Gif Images Without Search Query*/
        fetchGifImages(String())
        /*Swipe Refresh Reload Action*/
        swipeRefreshGifs()
        /*Show Initial Progress*/
        showProgressLoader()
    }

    /*Function for Initialising Gif Recycler View*/
    private fun initGifRecyclerView() {
        layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        binding.rvGiphy.layoutManager = layoutManager
        /*Gif Images Adapter For Displaying Gif Images*/
        adapter = GifImageAdapter(layoutManager, FavouritesClickListener {
            onFavouriteClicked(it)
        })
        /*Loader Adapter For Progress & Retry Event*/
        val loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        binding.rvGiphy.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    /*Search For Gifs*/
    private fun gifSearcher() {
        binding.svGifs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchGifs: String): Boolean {
                binding.svGifs.clearFocus()
                reloadGifData(searchGifs)
                return false
            }

            override fun onQueryTextChange(searchGifs: String): Boolean {
                reloadGifData(searchGifs)
                return false
            }
        })
    }

    /*Pull To Refresh For Refreshing Gif Data*/
    private fun swipeRefreshGifs() {
        binding.swipeRefresh.setOnRefreshListener {
            reloadGifData(String())
        }
    }

    /*Function for Fetching Fresh Gif Info*/
    private fun reloadGifData(searchGifs: String) {
        PAGE_OFFSET = 0
        fetchGifImages(searchGifs)
        layoutManager(searchGifs)
    }

    /*Reload Layout Manager When Search Action Performed*/
    private fun layoutManager(searchGifs: String) {
        if (searchGifs.isNotEmpty()) layoutManager.spanCount =
            LIST_COLUMN else layoutManager.spanCount = GRID_COLUMNS
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        binding.rvGiphy.snapToPosition(0)
    }

    /*Function for Fetching Trending Gif Images or Search Gif Images If Search Query Present*/
    private fun fetchGifImages(searchGifs: String) {
        lifecycleScope.launch {
            giphyGifViewModel.fetchGifImages(searchGifs).distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Show Progress Loader*/
    private fun showProgressLoader() {
        lifecycleScope.launch {
            adapter.loadStateFlow.collectLatest { loadStates ->
                binding.swipeRefresh.isRefreshing = loadStates.refresh is LoadState.Loading
            }
        }
    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(gifInfo: GifInfo) {
        lifecycleScope.launch {
            giphyGifViewModel.toggleFavourites(gifInfo)
            requireContext().shortToast(getString(R.string.toggle_favourites))
        }
    }
}