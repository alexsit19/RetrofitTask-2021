package com.example.retrofittask_2021

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.retrofittask_2021.databinding.CatItemBinding
import com.example.retrofittask_2021.network.CatPhoto

class CatAdapter(
    private val listener: CatListener
    ): ListAdapter<CatPhoto, CatViewHolder>(CatComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding, listener, parent.context)
    }

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        val catPhoto = getItem(position)
        holder.bind(catPhoto)
    }

}

class CatComparator: DiffUtil.ItemCallback<CatPhoto>() {
    override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
        return oldItem == newItem

    }
}