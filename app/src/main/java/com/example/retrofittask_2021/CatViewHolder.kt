package com.example.retrofittask_2021

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.example.retrofittask_2021.databinding.CatItemBinding
import com.example.retrofittask_2021.network.CatPhoto

class CatViewHolder(
    private val binding: CatItemBinding,
    private val listener: CatListener,

) : RecyclerView.ViewHolder(binding.root) {

    private var itemPosition: Int? = null

    fun bind(catPhoto: CatPhoto, position: Int) {
        binding.catItemIv.load(catPhoto.imgSrcUrl) {
            itemPosition = position
            placeholder(R.drawable.ic_baseline_image_24)
        }

        binding.catItemIv.setOnClickListener {
            listener.openDetailFragment(catPhoto)
        }
    }
}