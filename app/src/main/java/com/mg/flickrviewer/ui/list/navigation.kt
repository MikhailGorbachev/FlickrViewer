package com.mg.flickrviewer.ui.list

import androidx.navigation.fragment.findNavController

fun RecentPhotosFragment.openDetails() {
    val action = RecentPhotosFragmentDirections.photoDetailsAction()
    findNavController().navigate(action)

}