package com.mg.flickrviewer.api

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class FlickrPhoto(
    val id: String?,
    val title: String?,
    //Thumbnail and small size
    val url_sq: String?,
    val url_s: String?,
    val url_q: String?,
    val url_t: String?,
    val url_m: String?,
    val url_n: String?,
    val url_w: String?,

    //Medium and large size
    val url_b: String?,
    val url_c: String?,
    val url_z: String?,

    ) : Parcelable {

    fun getThumbnailUrl() = url_t ?: url_q ?: url_s ?: url_sq

    fun getUrl() = url_b ?: url_c ?: url_z ?: getThumbnailUrl()
}

data class PhotosResponse(
    val photos: PaginationResponse
)

data class PaginationResponse(
    val page: Long,
    val pages: Long,
    val perpage: Long,
    val total: Long,
    val photo: List<FlickrPhoto>
)