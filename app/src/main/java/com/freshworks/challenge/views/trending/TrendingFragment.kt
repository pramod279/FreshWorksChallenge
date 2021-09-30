package com.freshworks.challenge.views.trending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
    private val trendingGifViewModel: TrendingViewModel by viewModels()

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

        /*Fetch Trending Gif Images*/
        //fetchTrendingGifs(adapter)

        /*Fetch Favourite Gif Images*/
        fetchMyFavourites(adapter)
    }

    /*Function for Fetching Trending Gif Images*/
    private fun fetchTrendingGifs(adapter: GifImageAdapter) {
        lifecycleScope.launch {
            trendingGifViewModel.fetchTrendingGifs().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Function for Fetching My Favourite Gif Images*/
    private fun fetchMyFavourites(adapter: GifImageAdapter) {

    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(gifInfo: GifInfo) {
        Toast.makeText(context, "Favourites !!! ${gifInfo.id}", Toast.LENGTH_SHORT).show()
        // trendingGifViewModel.addToFavourites(gifInfo)
        // trendingGifViewModel.removeFromFavourites(gifInfo.id)
    }
}