package com.appfactorycoding.section.search_detail.data.remote

import com.appfactorycoding.service.model.search_detail.SearchDetailResponse
import kotlinx.coroutines.flow.Flow

interface SearchDetailRepository {
   suspend fun getSelectedGalleryItem(objectID:Int): Flow<SearchDetailResponse>
}