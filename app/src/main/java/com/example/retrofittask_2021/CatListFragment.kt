package com.example.retrofittask_2021

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofittask_2021.databinding.CatListFragmentBinding
import kotlinx.coroutines.flow.collectLatest

class CatListFragment: Fragment() {

    private var _binding: CatListFragmentBinding? = null
    private val binding: CatListFragmentBinding get() = requireNotNull(_binding)
    private val viewModel: MainViewModel by activityViewModels()
    //private var catAdapter: CatAdapter? = null
    private var catPagingAdapter: CatPagingAdapter? = null
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        catPagingAdapter = listener?.let { CatPagingAdapter(it) }
        lifecycleScope.launchWhenCreated {
            viewModel.getPagingCatPhotos()?.collectLatest {
                catPagingAdapter?.submitData(it)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //catPagingAdapter = listener?.let { CatPagingAdapter(it) }
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = catPagingAdapter
        binding.recycler.smoothScrollToPosition(10)
        //catPagingAdapter?.refresh()
        Log.d("DEBUG", "onview Created")

//        viewModel.photos.observe(viewLifecycleOwner, Observer {
//            Log.d("DEBUG", "List: ${it.toString()}")
//            Log.d("DEBUG", "List size: ${it.size}")
//            catPagingAdapter?.submitData(viewModel.photos.value)
    //})



    }

    override fun onDestroy() {
        _binding = null
        Log.d("DEBUG", "ONDESTROY_LIST")
        super.onDestroy()
    }
}