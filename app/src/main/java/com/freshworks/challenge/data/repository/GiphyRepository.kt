package com.freshworks.challenge.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.freshworks.challenge.data.remote.GiphyPagingSource
import com.freshworks.challenge.db.FavouritesDao
import com.freshworks.challenge.data.entities.GifInfo
import com.freshworks.challenge.network.GiphyApiService
import com.freshworks.challenge.utilities.Constants.NETWORK_PAGE_SIZE
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
    /*Fetch Gif Images Using Pagination Trending & Search*/
    fun letGifImagesFlow(
        searchGifs: String,
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): Flow<PagingData<GifInfo>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { GiphyPagingSource(searchGifs, service) }
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
    suspend fun isFavourite(gifInfo: GifInfo): Boolean {
        return favouritesDao.isFavourite(gifInfo.id)
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
}