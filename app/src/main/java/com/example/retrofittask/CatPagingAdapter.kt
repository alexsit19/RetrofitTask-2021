package com.example.retrofittask

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.retrofittask_2021.databinding.CatItemBinding
import com.example.retrofittask.network.CatPhoto

class CatPagingAdapter(
    private val listener: CatListener
) : PagingDataAdapter<CatPhoto, CatViewHolder>(DiffUtilCallBack()) {

    override fun onBindViewHolder(holder: CatViewHolder, position: Int) {
        getItem(position)?.let{ holder.bind(it, position) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CatItemBinding.inflate(layoutInflater, parent, false)
        return CatViewHolder(binding, listener)
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<CatPhoto>() {

    override fun areItemsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CatPhoto, newItem: CatPhoto): Boolean {
        return oldItem == newItem
    }
}
