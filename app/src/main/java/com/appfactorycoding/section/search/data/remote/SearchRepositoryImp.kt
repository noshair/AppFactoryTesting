package com.appfactorycoding.section.search.data.remote

import com.appfactorycoding.service.api_service.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SearchRepositoryImp @Inject constructor(private val apiService: ApiService):
    SearchRepository {
    override fun getSearchData(categoryType:String) =flow {
        emit(apiService.getSearchData(categoryType))
    }.flowOn(Dispatchers.IO)
}