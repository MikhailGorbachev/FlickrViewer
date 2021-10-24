package com.mg.flickrviewer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.mg.flickrviewer.R
import com.mg.flickrviewer.databinding.LoadingListItemBinding

class PhotosLoadingStateAdapter(
    private val adapter: PhotosDataAdapter
) : LoadStateAdapter<PhotosLoadingStateAdapter.LoadingStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        LoadingStateItemViewHolder(
            LoadingListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
        { adapter.retry() }

    override fun onBindViewHolder(holder: LoadingStateItemViewHolder, loadState: LoadState) =
        holder.bind(loadState)

    override fun getStateViewType(loadState: LoadState) = when (loadState) {
        is LoadState.Loading, is LoadState.Error -> 1
        else -> 0
    }

    class LoadingStateItemViewHolder(
        private val binding: LoadingListItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading

                if (loadState is LoadState.Error) {
                    val errMessage =
                        loadState.error.message ?: root.context.getString(R.string.default_error)
                    Snackbar.make(root, errMessage, Snackbar.LENGTH_INDEFINITE).apply {
                        setAction(R.string.retry) { retryCallback() }
                        show()
                    }
                }
            }
        }
    }
}