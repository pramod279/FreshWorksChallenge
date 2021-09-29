package com.freshworks.challenge.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.freshworks.challenge.data.GiphyRepository.Companion.CURRENT_PAGE_OFFSET
import com.freshworks.challenge.data.GiphyRepository.Companion.DEFAULT_PAGE_INDEX
import com.freshworks.challenge.data.GiphyRepository.Companion.DEFAULT_PAGE_LIMIT
import com.freshworks.challenge.data.GiphyRepository.Companion.NETWORK_PAGE_SIZE
import com.freshworks.challenge.model.Data
import com.freshworks.challenge.repository.GiphyApiService
import retrofit2.HttpException
import java.io.IOException

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 */
class GifImagePagingSource(
    private val service: GiphyApiService,
) : PagingSource<Int, Data>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val position = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = service.getTrendingGifs(DEFAULT_PAGE_LIMIT, CURRENT_PAGE_OFFSET)
            /*Increment PAGE_OFFSET for Next Fetching Next Set of Gifs*/
            CURRENT_PAGE_OFFSET = response.pagination.count.plus(response.pagination.offset)
            val repos = response.data
            val nextKey = if (repos.isEmpty()) {
                null
            } else {
                // initial load size = 3 * NETWORK_PAGE_SIZE
                // ensure we're not requesting duplicating items, at the 2nd request
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = repos,
                prevKey = if (position == DEFAULT_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}