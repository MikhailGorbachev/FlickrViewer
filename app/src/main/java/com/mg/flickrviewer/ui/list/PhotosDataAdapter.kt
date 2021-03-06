package com.mg.flickrviewer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mg.flickrviewer.R
import com.mg.flickrviewer.api.FlickrPhoto
import com.mg.flickrviewer.databinding.PhotoListItemBinding
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class PhotosDataAdapter(private val click: (FlickrPhoto) -> Unit) :
    PagingDataAdapter<FlickrPhoto, RecyclerView.ViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as? FlickrPhotoViewHolder)?.bind(item, click)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoListItemBinding.inflate(inflater, parent, false)
        return FlickrPhotoViewHolder(binding)
    }

    class FlickrPhotoViewHolder(private val binding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FlickrPhoto?, click: (FlickrPhoto) -> Unit) {
            binding.run {
                imageProgressBar.isVisible = true
                imageView.setOnClickListener {
                    item?.let {
                        click(it)
                    }
                }
                Picasso.get()
                    .load(item?.getThumbnailUrl())
                    .error(R.drawable.ic_baseline_error_outline_24)
                    .into(imageView, object : Callback {
                        override fun onSuccess() {
                            imageProgressBar.isVisible = false
                        }

                        override fun onError(e: Exception?) {
                            imageProgressBar.isVisible = false
                        }
                    })
            }
        }

    }
}