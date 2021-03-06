package com.freshworks.challenge.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.data.repository.GiphyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * The ViewModel used in [TrendingFragment] & [FavouritesFragment]
 */
@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val repository: GiphyRepository
) : ViewModel() {

    /*Fetch Gif Images Using Pagination Trending & Search*/
    fun fetchGifImages(searchGifs: String): Flow<PagingData<GifInfo>> {
        return repository.letGifImagesFlow(searchGifs)
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }

    /*Fetch My Favourite Gif Images Using Pagination*/
    fun fetchMyFavourites(): Flow<PagingData<GifInfo>> {
        return repository.letMyFavouritesFlow()
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }

    /*Adding/Removing Gif Image To Favourites*/
    fun toggleFavourites(gifInfo: GifInfo) {
        viewModelScope.launch {
            when {
                isFavourite(gifInfo) -> removeFromFavourites(gifInfo.id)
                else -> addToFavourites(gifInfo)
            }
        }
    }

    /*Adding Gif Image To Favourites*/
    private fun addToFavourites(gifInfo: GifInfo) {
        viewModelScope.launch {
            repository.markFavourite(gifInfo)
        }
    }

    /*Removing Gif Image From Favourites*/
    private fun removeFromFavourites(gifId: String) {
        viewModelScope.launch {
            repository.unMarkFavourite(gifId)
        }
    }

    /*Check If The Gif Image Is Favourite Or Not*/
    private suspend fun isFavourite(gifInfo: GifInfo): Boolean {
        return repository.isFavourite(gifInfo)
    }
}