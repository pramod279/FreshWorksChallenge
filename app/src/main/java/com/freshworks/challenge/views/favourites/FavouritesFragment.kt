package com.freshworks.challenge.views.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.challenge.databinding.FragmentFavouritesBinding
import com.freshworks.challenge.model.GifInfo
import com.freshworks.challenge.views.loader.LoaderStateAdapter
import com.freshworks.challenge.views.trending.GiphyViewModel
import com.freshworks.challenge.views.trending.adapters.GifImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
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
    private val giphyGifViewModel: GiphyViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)
        subscribeUi(binding)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentFavouritesBinding) {
        binding.rvGiphy.layoutManager = GridLayoutManager(context, 2)
        /*Gif Images Adapter For Displaying Gif Images*/
        val adapter = GifImageAdapter(GifImageAdapter.FavouritesClickListener {
            onFavouriteClicked(it)
        })
        /*Loader Adapter For Progress & Retry Event*/
        val loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        binding.rvGiphy.adapter = adapter.withLoadStateFooter(loaderStateAdapter)

        /*Fetch My Favourite Gifs Images*/
        fetchMyFavouriteGifs(adapter)
    }

    /*Function for Fetching My Favourite Gif Images*/
    private fun fetchMyFavouriteGifs(adapter: GifImageAdapter) {
        lifecycleScope.launch {
            giphyGifViewModel.fetchMyFavourites().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(gifInfo: GifInfo) {
        lifecycleScope.launch {
            giphyGifViewModel.toggleFavourites(gifInfo)
        }
    }
}