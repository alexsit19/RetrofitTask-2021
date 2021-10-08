package com.example.retrofittask

import com.example.retrofittask.network.CatPhoto

interface CatListener {

    fun openDetailFragment(catPhoto: CatPhoto)

    fun openListFragment()
}
