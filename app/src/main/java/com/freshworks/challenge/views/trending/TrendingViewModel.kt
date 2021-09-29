package com.freshworks.challenge.views.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.freshworks.challenge.data.GiphyRepository
import com.freshworks.challenge.model.GifInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * Trending Gifs View Model Class for Trending Fragment
 */
class TrendingViewModel(
    private val repository: GiphyRepository = GiphyRepository.getInstance()
) : ViewModel() {
    /**
     * We just mapped the data received from the repository to [PagingData<Data>] to show the map
     * function you can always return the original model if needed, in our case it would be [GifInfo]
     */
    fun fetchGifImages(): Flow<PagingData<GifInfo>> {
        return repository.letGifImagesFlow()
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }
}