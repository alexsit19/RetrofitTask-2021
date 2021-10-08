package com.example.retrofittask

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.retrofittask_2021.databinding.CatListFragmentBinding
import kotlinx.coroutines.flow.collectLatest

class CatListFragment : Fragment() {

    private var _binding: CatListFragmentBinding? = null
    private val binding: CatListFragmentBinding get() = requireNotNull(_binding)
    private val viewModel: MainViewModel by activityViewModels()
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
        binding.recycler.layoutManager = LinearLayoutManager(requireActivity())
        binding.recycler.adapter = catPagingAdapter
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}
