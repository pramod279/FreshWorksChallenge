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
    private val viewModel: TrendingViewModel by viewModels()

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

        /*Fetch Gif Images*/
        fetchGifImages(adapter)
    }

    /*Function for Fetching All Gif Images*/
    private fun fetchGifImages(adapter: GifImageAdapter) {
        lifecycleScope.launch {
            viewModel.fetchGifImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(gifInfo: GifInfo) {
        Toast.makeText(context, "Favourites !!! ${gifInfo.title}", Toast.LENGTH_SHORT).show()
        //viewModel.addGifToFavourites(gifInfo)
    }
}