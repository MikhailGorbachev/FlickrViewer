package com.mg.flickrviewer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mg.flickrviewer.databinding.RecentPhotosFragmentBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class RecentPhotosFragment : Fragment() {

    private val viewModel by sharedViewModel<RecentPhotosViewModel>()

    private var binding: RecentPhotosFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = RecentPhotosFragmentBinding.inflate(inflater, container, false).apply {
        binding = this
    }.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listAdapter = PhotosDataAdapter()

        binding?.run {
            photoList.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = listAdapter
            }

            swiperefresh.setOnRefreshListener {
                listAdapter.refresh()
            }

            viewModel.photoList.observe(viewLifecycleOwner) {
                swiperefresh.isRefreshing = false

                lifecycleScope.launch {
                    listAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}