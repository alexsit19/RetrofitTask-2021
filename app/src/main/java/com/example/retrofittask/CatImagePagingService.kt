package com.example.retrofittask

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.retrofittask.network.CatApiService
import com.example.retrofittask.network.CatPhoto
import java.net.ConnectException

private const val CAT_API_STARTING_PAGE_INDEX = 0
private const val PAGE_SIZE = 20

class CatImagePagingService(private val catApiService: CatApiService) :
    PagingSource<Int, CatPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CatPhoto> {

        return try {
            val pageIndex = params.key ?: CAT_API_STARTING_PAGE_INDEX
            val pageSize = PAGE_SIZE

            val catsList = catApiService.getPhotos(page = pageIndex, limit = pageSize)
            if (catsList.isNotEmpty()) {
                val nextKey = if (catsList.size < pageSize) null else pageIndex + 1
                val prevKey = if (pageIndex == 0) null else pageIndex - 1
                LoadResult.Page(catsList, prevKey, nextKey)
            } else {
                LoadResult.Page(emptyList(), null, null)
            }
        } catch (error: ConnectException) {
            LoadResult.Error(error)
        }
    }

    @ExperimentalPagingApi
    override fun getRefreshKey(state: PagingState<Int, CatPhoto>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val pageIndex = state.closestPageToPosition(anchorPosition) ?: return null
        return pageIndex.prevKey?.plus(1) ?: pageIndex.nextKey?.minus(1)
    }
}
