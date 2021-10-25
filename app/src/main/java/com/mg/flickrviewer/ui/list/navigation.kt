package com.mg.flickrviewer.ui.list

import androidx.navigation.fragment.findNavController
import com.mg.flickrviewer.api.FlickrPhoto

fun RecentPhotosFragment.openDetails(flickrPhoto: FlickrPhoto) {
    val action = RecentPhotosFragmentDirections.photoDetailsAction(flickrPhoto)
    findNavController().navigate(action)
}