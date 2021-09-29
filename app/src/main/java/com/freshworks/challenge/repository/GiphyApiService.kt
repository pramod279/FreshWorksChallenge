package com.freshworks.challenge.repository

import com.freshworks.challenge.model.GiphyData
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 */
interface GiphyApiService {
    @GET(ApiUrls.TRENDING)
    suspend fun getTrendingGifs(
        @Query(ApiUrls.LIMIT) limit: Int = 20,
        @Query(ApiUrls.OFFSET) offset: Int = 0
    ): GiphyData

    @GET(ApiUrls.SEARCH)
    suspend fun searchForGifs(
        @Query(ApiUrls.SEARCH_QUERY) searchQuery: String,
        @Query(ApiUrls.LIMIT) limit: Int = 20,
        @Query(ApiUrls.OFFSET) offset: Int = 0
    ): GiphyData
}