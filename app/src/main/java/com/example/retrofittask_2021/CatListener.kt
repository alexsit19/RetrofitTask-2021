package com.example.retrofittask_2021

import com.example.retrofittask_2021.network.CatPhoto

interface CatListener {

    fun openDetailFragment(catPhoto: CatPhoto)

    fun openListFragment()
}
