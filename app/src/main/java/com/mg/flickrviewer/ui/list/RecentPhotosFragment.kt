package com.mg.flickrviewer.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mg.flickrviewer.databinding.RecentPhotosFragmentBinding
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

        binding?.run {
            button.setOnClickListener {
                openDetails()
            }
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}