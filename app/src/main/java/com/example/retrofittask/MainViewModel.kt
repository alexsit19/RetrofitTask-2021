package com.example.retrofittask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.retrofittask.network.CatApi
import com.example.retrofittask.network.CatPhoto
import kotlinx.coroutines.flow.Flow

class MainViewModel : ViewModel() {

    fun getPagingCatPhotos(): Flow<PagingData<CatPhoto>> {
        return Pager(
            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { CatImagePagingService(CatApi.retrofitService) }
        ).flow.cachedIn(viewModelScope)
    }
}
