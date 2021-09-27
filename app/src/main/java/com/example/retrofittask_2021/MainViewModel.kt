package com.example.retrofittask_2021

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofittask_2021.network.CatApi
import com.example.retrofittask_2021.network.CatPhoto
import kotlinx.coroutines.launch

enum class CatApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val _status = MutableLiveData<CatApiStatus>()

    val status: LiveData<CatApiStatus> = _status

    private val _photos = MutableLiveData<List<CatPhoto>>()

    val photos: LiveData<List<CatPhoto>> = _photos

    init {
        getCatPhotos()
        Log.d("DEBUG", "List from viewModel: ${photos.value.toString()}")


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