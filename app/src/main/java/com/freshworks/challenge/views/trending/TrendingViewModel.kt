package com.freshworks.challenge.views.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.freshworks.challenge.data.GiphyRepository
import com.freshworks.challenge.model.GifInfo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * The ViewModel used in [TrendingFragment].
 */
@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    /*Fetch Trending Gif Images Using Pagination*/
    fun fetchTrendingGifs(): Flow<PagingData<GifInfo>> {
        return repository.letTrendingGifsFlow()
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }

    /*Adding Gif Image To Favourites*/
    fun addToFavourites(gifInfo: GifInfo) {
        viewModelScope.launch {
            repository.markFavourite(gifInfo)
        }
    }

    /*Removing Gif Image From Favourites*/
    fun removeFromFavourites(gifId: String) {
        viewModelScope.launch {
            repository.unMarkFavourite(gifId)
        }
    }
}