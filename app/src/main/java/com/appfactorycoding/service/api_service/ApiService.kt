package com.appfactorycoding.service.api_service

import com.appfactorycoding.service.model.search.SearchResponse
import com.appfactorycoding.service.model.search_detail.SearchDetailResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("/public/collection/v1/search?")
    suspend fun getSearchData(
        @Query("q") art: String,
        @Query("hasImages") hasImages: Boolean=true
    ): SearchResponse

    @GET("/public/collection/v1/objects/{objectID}")
    suspend fun getGalleryItem(@Path("objectID") id: Int): SearchDetailResponse
}