package com.freshworks.challenge.views.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.freshworks.challenge.databinding.FragmentTrendingBinding
import com.freshworks.challenge.model.GifInfo
import com.freshworks.challenge.views.loader.LoaderStateAdapter
import com.freshworks.challenge.views.trending.adapters.GifImageAdapter
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTrendingBinding.inflate(inflater, container, false)
        subscribeUi(binding)
        return binding.root
    }

    private fun subscribeUi(binding: FragmentTrendingBinding) {
        binding.rvGiphy.layoutManager = GridLayoutManager(context, 2)
        /*Gif Images Adapter For Displaying Gif Images*/
        val adapter = GifImageAdapter(GifImageAdapter.FavouritesClickListener {
            onFavouriteClicked(it)
        })
        /*Loader Adapter For Progress & Retry Event*/
        val loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
        binding.rvGiphy.adapter = adapter.withLoadStateFooter(loaderStateAdapter)

        /*Fetch Trending Gif Images With Default No Search Query*/
        fetchGifImages(String(), adapter)

        /*Search for Gifs Using Search View*/
        binding.svGifs.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(searchGifs: String): Boolean {
                binding.svGifs.clearFocus()
                fetchGifImages(searchGifs, adapter)
                return false
            }

            override fun onQueryTextChange(searchGifs: String): Boolean {
                /*Fetch Trending List If Search Query Is Empty*/
                if (binding.svGifs.query.isEmpty()) {
                    binding.svGifs.clearFocus()
                    fetchGifImages(searchGifs, adapter)
                }
                return false
            }
        })
    }

    /*Function for Fetching Trending Gif Images or Search Gif Images If Search Query Present*/
    private fun fetchGifImages(searchGifs: String, adapter: GifImageAdapter) {
        lifecycleScope.launch {
            giphyGifViewModel.fetchGifImages(searchGifs).distinctUntilChanged().collectLatest {
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