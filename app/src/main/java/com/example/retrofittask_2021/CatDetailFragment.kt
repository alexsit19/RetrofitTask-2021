package com.example.retrofittask_2021

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.example.retrofittask_2021.databinding.CatDetailFragmentBinding
import com.example.retrofittask_2021.network.CatPhoto

class CatDetailFragment(val catPhoto: CatPhoto) : Fragment() {

    private var _binding: CatDetailFragmentBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CatDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.detailIv.load(catPhoto.imgSrcUrl)
    }
}