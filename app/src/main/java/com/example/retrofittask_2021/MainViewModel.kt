package com.example.retrofittask_2021

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.retrofittask_2021.network.CatPhoto

enum class CatApiStatus { LOADING, ERROR, DONE }

class MainViewModel: ViewModel() {

    private val _status = MutableLiveData<CatApiStatus>()

    val status: LiveData<CatApiStatus> = _status

    private val _photos = MutableLiveData<List<CatPhoto>>()
}