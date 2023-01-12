package com.appfactorycoding.section.search.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfactorycoding.section.search.data.remote.SearchRepositoryImp
import com.appfactorycoding.service.extension.Resource
import com.appfactorycoding.service.model.search.SearchResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepositoryImp: SearchRepositoryImp) :
    ViewModel() {
    private val searchMutableList =
        MutableStateFlow<Resource<SearchResponse>>(Resource.OnNothing())

    val searchList: StateFlow<Resource<SearchResponse>> get() = searchMutableList


     fun getSearch(searchItem:String) {
        viewModelScope.launch {
            searchMutableList.value = Resource.OnLoading()
            searchRepositoryImp.getSearchData(searchItem)
                .catch { e ->
                    searchMutableList.value = Resource.OnFailure(null, e.message)
                }.collect {
                    searchMutableList.value = Resource.OnSuccess(it)
                }
        }
    }
}