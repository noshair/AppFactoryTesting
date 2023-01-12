package com.appfactorycoding.section.search_detail.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appfactorycoding.section.search_detail.data.remote.SearchDetailRepositoryImp
import com.appfactorycoding.service.extension.Resource
import com.appfactorycoding.service.model.search_detail.SearchDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItemViewModel @Inject constructor(private val searchDetailRepositoryImp: SearchDetailRepositoryImp) :
    ViewModel() {
    private val searchDetailMutable =
        MutableStateFlow<Resource<SearchDetailResponse>>(Resource.OnNothing())

    val searchDetailItem: StateFlow<Resource<SearchDetailResponse>>
        get() = searchDetailMutable

    fun getSelectedItem(id: Int) {
        viewModelScope.launch {
            searchDetailMutable.value = Resource.OnLoading()
            searchDetailRepositoryImp.getSelectedGalleryItem(id)
                .catch { e ->
                    searchDetailMutable.value = Resource.OnFailure(null, e.message)
                }.collect {
                    searchDetailMutable.value = Resource.OnSuccess(it)
                }
        }
    }
}