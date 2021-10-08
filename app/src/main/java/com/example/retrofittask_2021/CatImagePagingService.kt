package com.example.retrofittask_2021

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofittask_2021.network.CatApiService
import com.example.retrofittask_2021.network.CatPhoto

private const val CAT_API_STARTING_PAGE_INDEX = 0

class CatImagePagingService(private val catApiService: CatApiService) :
    PagingSource<Int, CatPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatPhoto> {

        return try {
            val pageIndex = params.key ?: CAT_API_STARTING_PAGE_INDEX
            val pageSize = 20

            val catsList = catApiService.getPhotos(page = pageIndex, limit = pageSize)
            if (catsList.isNotEmpty()) {
                val nextKey = if (catsList.size < pageSize) null else pageIndex + 1
                val prevKey = if (pageIndex == 0) null else pageIndex - 1
                LoadResult.Page(catsList, prevKey, nextKey)
            } else {
                LoadResult.Page(emptyList(), null, null)
            }
        } catch (e: Exception) {
            Log.d("DEBUG", "MESSAGE ${e.message}")
            LoadResult.Error(e)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, CatPhoto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val pageIndex = state.closestPageToPosition(anchorPosition) ?: return null
        return pageIndex.prevKey?.plus(1) ?: pageIndex.nextKey?.minus(1)
    }
}
