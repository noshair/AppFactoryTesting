package com.appfactorycoding.section.search.data.remote

import com.appfactorycoding.service.model.search.SearchResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun getSearchData(categoryType:String): Flow<SearchResponse>
}