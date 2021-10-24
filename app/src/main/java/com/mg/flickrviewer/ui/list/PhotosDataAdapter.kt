package com.mg.flickrviewer.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mg.flickrviewer.api.FlickrPhoto
import com.mg.flickrviewer.databinding.PhotoListItemBinding
import com.squareup.picasso.Picasso

class PhotosDataAdapter : PagingDataAdapter<FlickrPhoto, RecyclerView.ViewHolder>(PHOTO_COMPARATOR) {

    companion object {
        private val PHOTO_COMPARATOR = object : DiffUtil.ItemCallback<FlickrPhoto>() {
            override fun areItemsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: FlickrPhoto, newItem: FlickrPhoto): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? FlickrPhotoViewHolder)?.bind(item = getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PhotoListItemBinding.inflate(inflater, parent, false)
        return FlickrPhotoViewHolder(binding)
    }

    class FlickrPhotoViewHolder(private val binding: PhotoListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FlickrPhoto?) {
            binding.run {
                Picasso.get().load(item?.url_sq).into(imageView)
            }
        }

    }
}