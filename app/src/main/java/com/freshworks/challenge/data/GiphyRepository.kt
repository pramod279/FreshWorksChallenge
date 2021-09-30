package com.freshworks.challenge.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freshworks.challenge.model.GifInfo
import com.freshworks.challenge.model.dao.FavouritesDao
import com.freshworks.challenge.repository.GiphyApiService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * @Author: Pramod Selvaraj
 * @Date: 28.09.2021
 *
 * Repository module for handling data operations.
 *
 * Collecting from the Flows in [GifInfo] is main-safe.
 */
@Singleton
class GiphyRepository @Inject constructor(
    private val service: GiphyApiService,
    private val favouritesDao: FavouritesDao
) {
    /*Fetch Trending Gif Images Using Pagination*/
    fun letTrendingGifsFlow(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<GifInfo>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { GiphyPagingSource(service) }
        ).flow
    }

    /*Fetch My Favourite Gif Images Using Pagination*/
    fun letMyFavouritesFlow(
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<GifInfo>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { favouritesDao.getMyFavourites() }
        ).flow
    }

    /**
     * Let's define page size, page size is the only required param
     */
    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = true)
    }

    /**
     * Marking Gif Images To Favourites
     */
    suspend fun markFavourite(gifInfo: GifInfo) {
        favouritesDao.insertFavourite(gifInfo)
    }

    /**
     * Removing Gif Images To Favourites
     */
    suspend fun unMarkFavourite(gifId: String) {
        favouritesDao.removeFavourite(gifId)
    }

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_LIMIT = 25
        const val NETWORK_PAGE_SIZE = 50
        var PAGE_OFFSET = 0
    }
}