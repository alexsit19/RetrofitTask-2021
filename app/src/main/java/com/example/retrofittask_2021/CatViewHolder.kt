package com.example.retrofittask_2021

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.GrayscaleTransformation
import com.example.retrofittask_2021.databinding.CatItemBinding
import com.example.retrofittask_2021.network.CatPhoto

class CatViewHolder(
    private val binding: CatItemBinding,
    private val listener: CatListener,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(catPhoto: CatPhoto) {
        binding.catItemIv.load(catPhoto.imgSrcUrl) {
            placeholder(R.drawable.ic_baseline_image_24)
        }

        binding.catItemIv.setOnClickListener {
            listener.openDetail(catPhoto)
        }
    }
}