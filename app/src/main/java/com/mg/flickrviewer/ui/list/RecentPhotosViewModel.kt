package com.mg.flickrviewer.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.mg.flickrviewer.repository.FlickrRepository

class RecentPhotosViewModel(flickrRepository: FlickrRepository) : ViewModel() {

    val photoList = flickrRepository.recentPhotosFlow().cachedIn(viewModelScope)

}