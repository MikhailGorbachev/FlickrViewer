package com.mg.flickrviewer.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mg.flickrviewer.api.FlickrPhoto
import com.mg.flickrviewer.api.FlickrService
import retrofit2.HttpException
import java.io.IOException

const val DEFAULT_PAGE_INDEX = 1

class PhotoPagingSource(private val flickrService: FlickrService) :
    PagingSource<Int, FlickrPhoto>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FlickrPhoto> {
        val page = params.key ?: DEFAULT_PAGE_INDEX
        return try {
            val response = flickrService.recent(page, params.loadSize)
            val photoList = response.photos.photo
            LoadResult.Page(
                photoList, prevKey = if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                nextKey = if (photoList.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FlickrPhoto>) = 0
}