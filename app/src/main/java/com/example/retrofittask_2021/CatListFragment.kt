package com.example.retrofittask_2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittask_2021.databinding.CatListFragmentBinding

class CatListFragment: Fragment() {

    private var _binding: CatListFragmentBinding? = null
    private val binding: CatListFragmentBinding get() = requireNotNull(_binding)
    private val viewModel: MainViewModel by viewModels()
    private var catAdapter: CatAdapter? = null
    var listener: CatListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = activity as MainActivity
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CatListFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catAdapter = listener?.let { CatAdapter(it) }
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = catAdapter

        viewModel.photos.observe(viewLifecycleOwner, Observer {
            Log.d("DEBUG", "List: ${it.toString()}")
            Log.d("DEBUG", "List size: ${it.size}")
            catAdapter?.submitList(viewModel.photos.value)
        })
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}