package com.mg.flickrviewer.api

data class FlickrPhoto(
    val id: String?,
    val title: String?,
    val url_sq: String?
)

data class RecentPhotosResponse(
    val photos: PaginationResponse
)

data class PaginationResponse(
    val page: Long,
    val pages: Long,
    val perpage: Long,
    val total: Long,
    val photo: List<FlickrPhoto>

)