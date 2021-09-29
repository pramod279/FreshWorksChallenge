package com.freshworks.challenge.views.trending

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.freshworks.challenge.R
import com.freshworks.challenge.model.Data
import com.freshworks.challenge.views.loader.LoaderStateAdapter
import com.freshworks.challenge.views.trending.adapters.GifImageAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 */
class TrendingFragment : Fragment(R.layout.fragment_trending) {
    lateinit var trendingViewModel: TrendingViewModel
    lateinit var rvGIFImages: RecyclerView
    private lateinit var adapter: GifImageAdapter
    lateinit var loaderStateAdapter: LoaderStateAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMembers()
        setUpViews(view)
        fetchGifImages()
    }

    private fun initMembers() {
        trendingViewModel = defaultViewModelProviderFactory.create(TrendingViewModel::class.java)
        adapter = GifImageAdapter(GifImageAdapter.FavouritesClickListener {
            onFavouriteClicked(it)
        })
        loaderStateAdapter = LoaderStateAdapter { adapter.retry() }
    }

    private fun setUpViews(view: View) {
        rvGIFImages = view.findViewById(R.id.rvGiphy)
        rvGIFImages.layoutManager = GridLayoutManager(context, 2)
        rvGIFImages.adapter = adapter.withLoadStateFooter(loaderStateAdapter)
    }

    private fun fetchGifImages() {
        lifecycleScope.launch {
            trendingViewModel.fetchGifImages().distinctUntilChanged().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    /*Mark/UnMark Gif As Favourites*/
    private fun onFavouriteClicked(data: Data) {
        Toast.makeText(context, "Favourites !!! ${data.title}", Toast.LENGTH_SHORT).show()
    }
}