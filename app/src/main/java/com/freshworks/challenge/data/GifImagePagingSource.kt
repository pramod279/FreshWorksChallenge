package com.freshworks.challenge.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freshworks.challenge.data.GifImagesRepository.Companion.DEFAULT_PAGE_INDEX
import com.freshworks.challenge.model.Data
import com.freshworks.challenge.repository.GiphyApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 */
class GifImagePagingSource(private val giphyApiService: GiphyApiService) :
    PagingSource<Int, Data>() {

    /**
     * calls api if there is any error getting results then return the [LoadResult.Error]
     * for successful response return the results using [LoadResult.Page] for some reason if the results
     * are empty from service like in case of no more data from api then we can pass [null] to
     * send signal that source has reached the end of list
     */
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        //for first case it will be null, then we can pass some default value, in our case it's 1
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = giphyApiService.getTrendingGifs(page, params.loadSize)
            val gifs = response.data
            LoadResult.Page(
                gifs, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (gifs.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        TODO("Not yet implemented")
    }
}