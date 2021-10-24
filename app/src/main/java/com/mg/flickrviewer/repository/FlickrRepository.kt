package com.mg.flickrviewer.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mg.flickrviewer.api.FlickrPhoto
import com.mg.flickrviewer.api.FlickrService

const val DEFAULT_PAGE_SIZE = 20

class FlickrRepository(private val flickrService: FlickrService) {

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = false)
    }

    fun searchPhotosFlow(
        searchText: String,
        pagingConfig: PagingConfig = getDefaultPageConfig()
    ): LiveData<PagingData<FlickrPhoto>> {
        return Pager(
            config = pagingConfig,
            pagingSourceFactory = { PhotoPagingSource(flickrService, searchText) }
        ).liveData
    }

}