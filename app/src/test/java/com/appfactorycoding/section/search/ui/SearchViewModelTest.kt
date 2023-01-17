package com.appfactorycoding.section.search.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.appfactorycoding.section.search.data.remote.SearchRepositoryImp
import com.appfactorycoding.service.model.search.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: SearchViewModel

    @Mock
    lateinit var searchRepositoryImp: SearchRepositoryImp

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel=SearchViewModel(searchRepositoryImp)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSearchedData() {
        runBlocking {
            val testOutSide = SearchResponse(listOf(4, 5), 2)
            Mockito.`when`(
                searchRepositoryImp.getSearchData(
                    "art"
                )
            ).thenReturn(flow { emit(testOutSide) })
            viewModel.getSearch("art")
            testDispatcher.scheduler.advanceUntilIdle()


            viewModel.searchList.test {
                val items=awaitItem()
                assertEquals(2, items.data?.total)

            }
        }
    }
}