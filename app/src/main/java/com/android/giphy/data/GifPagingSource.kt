package com.android.giphy.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.giphy.data.common.toGifs
import com.android.giphy.data.networking.GiphyService
import com.android.giphy.data.networking.model.Data
import com.android.giphy.model.Gif
import retrofit2.HttpException
import java.io.IOException

class GifPagingSource(
    private val service: GiphyService
) : PagingSource<Int, Data>() {

    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        return try {
            val pageNumber = params.key ?: 0
            val response = service.getTrendingGifs(pageNumber)
            val pageNum = response.pagination.offset
            val prevKey = if (pageNum > 0) pageNum - 1 else null
            val nextKey = pageNum + 1
            LoadResult.Page(
                data = response.data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}