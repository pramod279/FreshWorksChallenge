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
class GifImagesRepository(
    private val giphyApiService: GiphyApiService = RemoteInjector.injectGiphyApiService(),
    // val appDatabase: AppDatabase? = LocalInjector.injectDb()
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20

        //get Gif repository instance
        fun getInstance() = GifImagesRepository()
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
            pagingSourceFactory = { GifImagePagingSource(giphyApiService) }
        ).flow
    }

    /**
     * Let's define page size, page size is the only required param, rest is optional
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)
    }
}