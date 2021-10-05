package com.example.retrofittask_2021

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.retrofittask_2021.network.CatApi
import com.example.retrofittask_2021.network.CatPhoto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

enum class CatApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val paging = CatImagePagingService(CatApi.retrofitService)

    private var dataFlow: Flow<PagingData<CatPhoto>>? = null
    private val _status = MutableLiveData<CatApiStatus>()

    val status: LiveData<CatApiStatus> = _status

    private val _photos = MutableLiveData<List<CatPhoto>>()

    val photos: LiveData<List<CatPhoto>> = _photos

    init {
        Log.d("DEBUG", "INIT VIEWMODEL")

    }

    fun getPagingCatPhotos(): Flow<PagingData<CatPhoto>>? {
        if (dataFlow != null) {
            Log.d("DEBUG", "IF FLOW")
            return dataFlow

        } else {
            dataFlow = Pager (
            config = PagingConfig(pageSize = 90, initialLoadSize = 30, enablePlaceholders = false),
            pagingSourceFactory = { CatImagePagingService(CatApi.retrofitService) }
                ).flow.map { it }//cachedIn(viewModelScope)
        }
        return dataFlow

//        return Pager (
//            config = PagingConfig(pageSize = 30, enablePlaceholders = false),
//            pagingSourceFactory = { CatImagePagingService(CatApi.retrofitService) }
//                ).flow.cachedIn(viewModelScope)
    }

    private fun getCatPhotos() {
        viewModelScope.launch {
            _status.value = CatApiStatus.LOADING
            try {
                _photos.value = CatApi.retrofitService.getPhotos(100, 0, "asc", "med")
                _status.value = CatApiStatus.DONE
                Log.d("DEBUG", "TRY")
            } catch (e: Exception) {
                _status.value = CatApiStatus.ERROR
                _photos.value = listOf()
                Log.d("DEBUG", "Ex: ${e.message}")
            }
        }
    }
}