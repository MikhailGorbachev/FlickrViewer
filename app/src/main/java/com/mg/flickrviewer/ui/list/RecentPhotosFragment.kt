package com.mg.flickrviewer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.mg.flickrviewer.R
import com.mg.flickrviewer.databinding.RecentPhotosFragmentBinding
import com.mg.flickrviewer.utils.hideKeyboard
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
            val searchView = toolbar.menu.findItem(R.id.search_item).actionView as? SearchView
            searchView?.apply {
                maxWidth = Int.MAX_VALUE

                setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        viewModel.search(query.orEmpty())
                        hideKeyboard(requireActivity())
                        return true
                    }

                    override fun onQueryTextChange(newText: String?) = true
                })
            }

            photoList.apply {
                layoutManager = GridLayoutManager(context, 2).apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            val type = adapter?.getItemViewType(position)
                            return if (type == 1) {
                                2
                            } else {
                                1
                            }
                        }
                    }
                }
                adapter = listAdapter.run {
                    withLoadStateFooter(PhotosLoadingStateAdapter(this))
                }
            }

            swiperefresh.setOnRefreshListener {
                listAdapter.refresh()
            }

            viewModel.search("")

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