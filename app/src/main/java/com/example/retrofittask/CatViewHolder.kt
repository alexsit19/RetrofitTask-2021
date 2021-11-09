package com.example.retrofittask

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.retrofittask_2021.R
import com.example.retrofittask_2021.databinding.CatItemBinding
import com.example.retrofittask.network.CatPhoto

class CatViewHolder(
    private val binding: CatItemBinding,
    private val listener: CatListener
) : RecyclerView.ViewHolder(binding.root) {

    private var itemPosition: Int? = null

    fun bind(catPhoto: CatPhoto, position: Int) {
        binding.catItemImageView.load(catPhoto.imgSrcUrl) {
            itemPosition = position
            placeholder(R.drawable.ic_baseline_image_24)
        }

        binding.catItemImageView.setOnClickListener {
            listener.openDetailFragment(catPhoto)
        }
    }
}
