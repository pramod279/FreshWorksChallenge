package com.freshworks.challenge.repository

import com.freshworks.challenge.model.GiphyData
import com.freshworks.challenge.utilities.DEFAULT_PAGE_LIMIT
import com.freshworks.challenge.utilities.PAGE_OFFSET
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 *
 * Giphy API Service Class For Managing API Calls
 */
interface GiphyApiService {
    @GET(ApiUrls.TRENDING)
    suspend fun getTrendingGifs(
        @Query(ApiUrls.LIMIT) limit: Int = DEFAULT_PAGE_LIMIT,
        @Query(ApiUrls.OFFSET) offset: Int = PAGE_OFFSET
    ): GiphyData

    @GET(ApiUrls.SEARCH)
    suspend fun searchForGifs(
        @Query(ApiUrls.SEARCH_QUERY) searchQuery: String,
        @Query(ApiUrls.LIMIT) limit: Int = DEFAULT_PAGE_LIMIT,
        @Query(ApiUrls.OFFSET) offset: Int = PAGE_OFFSET
    ): GiphyData
}