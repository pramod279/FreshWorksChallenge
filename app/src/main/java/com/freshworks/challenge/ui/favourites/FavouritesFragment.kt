package com.freshworks.challenge.ui.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.databinding.FragmentFavouritesBinding
import com.freshworks.challenge.ui.common.LoaderStateAdapter
import com.freshworks.challenge.ui.trending.adapters.GifImageAdapter
import com.freshworks.challenge.ui.trending.adapters.GiphyViewHolder.FavouritesClickListener
import com.freshworks.challenge.utilities.Constants.GRID_COLUMNS
import com.freshworks.challenge.viewmodel.GiphyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * Tab 2 ==> Display all My Favourite Gif Images
 */
@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private val giphyViewModel: GiphyViewModel by viewModels()

    private lateinit var layoutManager: GridLayoutManager
    private lateinit var adapter: GifImageAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        subscribeUi()
        return binding.root
    }

    private fun subscribeUi() {
        /*Initialise Favourites Recycler View*/
        initGifRecyclerView()
        /*Fetch My Favourite Gifs Images*/
        fetchMyFavouriteGifs(adapter)
    }

    /*Function for Initialising Gif Recycler View*/
    private fun initGifRecyclerView() {
        layoutManager = GridLayoutManager(context, GRID_COLUMNS)
        binding.rvGiphy.layoutManager = layoutManager
        /*Gif Images Adapter For Displaying My Favourites*/
        adapter = GifImageAdapter(layoutManager, FavouritesClickListener {
            onFavouriteClicked(it)
        })
        /*Loader Adapter For Indicating Error & Retry Events*/
        binding.rvGiphy.adapter = adapter.withLoadStateFooter(
            footer = LoaderStateAdapter { adapter.retry() }
        )
    }

    /*Function for Fetching My Favourite Gif Images*/
    private fun fetchMyFavouriteGifs(adapter: GifImageAdapter) {
        lifecycleScope.launch {
            giphyViewModel.fetchMyFavourites().collectLatest {
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
}