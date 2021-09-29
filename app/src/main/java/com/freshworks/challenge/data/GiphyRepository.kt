package com.freshworks.challenge.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import com.freshworks.challenge.model.Data
import com.freshworks.challenge.repository.GiphyApiService
import com.freshworks.challenge.repository.RemoteInjector
import kotlinx.coroutines.flow.Flow

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 */
class GiphyRepository(
    private val service: GiphyApiService = RemoteInjector.injectGiphyApiService(),
    // val appDatabase: AppDatabase? = LocalInjector.injectDb()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_LIMIT = 25
        const val NETWORK_PAGE_SIZE = 50
        var CURRENT_PAGE_OFFSET = 0

        //get Gif repository instance
        fun getInstance() = GiphyRepository()
    }

    /**
     * Calling the paging source to give results from api calls
     * and returning the results in the form of flow [Flow<PagingData<GifImageModel>>]
     * since the [PagingDataAdapter] accepts the [PagingData] as the source in later stage
     */
    fun letGifImagesFlow(pagingConfig: PagingConfig = getDefaultPageConfig())
            : Flow<PagingData<Data>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { GifImagePagingSource(service) }
        ).flow
    }

    /**
     * Let's define page size, page size is the only required param, rest is optional
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true)
    }
}