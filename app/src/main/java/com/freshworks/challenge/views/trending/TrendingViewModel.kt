package com.freshworks.challenge.views.trending

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.freshworks.challenge.data.GifImagesRepository
import com.freshworks.challenge.model.Data
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 */
class TrendingViewModel(
    private val repository: GifImagesRepository = GifImagesRepository.getInstance()
) : ViewModel() {

    /**
     * We just mapped the data received from the repository to [PagingData<String>] to show the map
     * function you can always return the original model if needed, in our case it would be [GifImageModel]
     */
    fun fetchGifImages(): Flow<PagingData<Data>> {
        return repository.letGifImagesFlow()
            .map { it.map { it } }
            .cachedIn(viewModelScope)
    }
}