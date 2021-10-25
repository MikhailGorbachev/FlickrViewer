package com.mg.flickrviewer.api

import com.mg.flickrviewer.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

const val URLS = "url_sq, url_s, url_q, url_t, url_m, url_n, url_w, url_b, url_c, url_z"

interface FlickrService {

    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    suspend fun recent(
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_KEY,
        @Query("extras") extras: String = URLS
    ): PhotosResponse

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json")
    suspend fun search(
        @Query("page") page: Int,
        @Query("per_page") limit: Int,
        @Query("text") text: String,
        @Query("api_key") apiKey: String = BuildConfig.FLICKR_KEY,
        @Query("extras") extras: String = URLS
    ): PhotosResponse
}