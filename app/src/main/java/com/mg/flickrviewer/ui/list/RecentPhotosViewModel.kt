package com.mg.flickrviewer.ui.list

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mg.flickrviewer.api.FlickrPhoto
import com.mg.flickrviewer.repository.FlickrRepository

class RecentPhotosViewModel(private val flickrRepository: FlickrRepository) : ViewModel() {

    private val searchText = MutableLiveData<String>()

    val photoList: LiveData<PagingData<FlickrPhoto>> = searchText.switchMap { query ->
        flickrRepository.searchPhotosFlow(query).cachedIn(viewModelScope)
    }

    fun search(query: String) {
        searchText.value = query
    }


}