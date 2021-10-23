package com.mg.flickrviewer.api

import okhttp3.Response
import retrofit2.http.GET
import retrofit2.http.Query

const val URLS = "url_sq, url_t, url_s, url_q, url_m, url_n, url_z, url_c, url_l, url_o"

interface FlickrService {

    @GET("services/rest/?method=flickr.photos.getRecent&nojsoncallback=1&format=json")
    suspend fun recent(
        @Query("api_key") apiKey: String,
        @Query("extras") extras: String = URLS
    ): Response

    @GET("services/rest/?method=flickr.photos.search&nojsoncallback=1&format=json")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("text") text: String? = null,
        @Query("extras") extras: String = URLS
    ): Response
}