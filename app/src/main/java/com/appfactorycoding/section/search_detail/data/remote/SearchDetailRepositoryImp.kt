package com.appfactorycoding.section.search_detail.data.remote

import com.appfactorycoding.service.api_service.ApiService
import com.appfactorycoding.service.model.search_detail.SearchDetailResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchDetailRepositoryImp @Inject constructor(private val apiService: ApiService) :
    SearchDetailRepository {

    override suspend fun getSelectedGalleryItem(objectID: Int) = flow {
        emit(apiService.getGalleryItem(objectID))
    }.flowOn(Dispatchers.IO)
}