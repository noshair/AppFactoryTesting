package com.appfactorycoding.section.search_detail.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.cash.turbine.test
import com.appfactorycoding.section.search_detail.data.remote.SearchDetailRepositoryImp
import com.appfactorycoding.service.model.search_detail.ElementMeasurements
import com.appfactorycoding.service.model.search_detail.Measurement
import com.appfactorycoding.service.model.search_detail.SearchDetailResponse
import com.appfactorycoding.service.model.search_detail.Tag
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class SearchItemViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: SearchItemViewModel
    @Mock
    lateinit var searchDetailRepositoryImp: SearchDetailRepositoryImp

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel= SearchItemViewModel(searchDetailRepositoryImp)
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun getSearchDetailItem() {
        runTest {
            Mockito.`when`(
                searchDetailRepositoryImp.getSelectedGalleryItem(38153)
            ).thenReturn(flow {
               emit( SearchDetailResponse(
                    "241",
                    "2015.500.4.14",
                    "2015",
                    listOf(
                        "https://images.metmuseum.org/CRDImages/as/original/DP-1062-002.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP-1062-003.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP-1062-004.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP-1062-005.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP-1062-006.jpg",
                        "https://images.metmuseum.org/CRDImages/as/original/DP244584.jpg"
                    ),
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "",
                    "Sculpture",
                    listOf(),
                    "",
                    "",
                    "Gift of Florence and Herbert Irving, 2015",
                    "Central India, Madhya Pradesh",
                    "Asian Art",
                    "H. 34 3/4 in. (88.3 cm); W. 20 in.(50.8 cm); D. 12 1/2 in. (31.8 cm); Wt. (with block) 170 lb (77.1 kg)",
                    "",
                    "",
                    "",
                    true,
                    true,
                    false,
                    "",
                    "",
                    "",
                    listOf(Measurement("null", ElementMeasurements(88.265175, 50.800102), "")),
                    "Sandstone",
                    "2022-08-09T15:23:22.823Z",
                    1034,
                    "mid-11th century",
                    1034,
                    38153,
                    "Figure",
                    "https://www.metmuseum.org/art/collection/search/38153",
                    "https://www.wikidata.org/wiki/Q48995767",
                    "Chandela period",
                    "",
                    "https://images.metmuseum.org/CRDImages/as/original/DP-1062-001.jpg",
                    "https://images.metmuseum.org/CRDImages/as/web-large/DP-1062-001.jpg",
                    "",
                    "",
                    "Metropolitan Museum of Art, New York, NY",
                    "",
                    "",
                    "",
                    "",
                    listOf(
                        Tag(
                            "Dancers",
                            "http://vocab.getty.edu/page/aat/300025653",
                            "https://www.wikidata.org/wiki/Q5716684"
                        )
                    ),
                    "Celestial dancer (Devata)"
                ))
            }
            )
            viewModel.getSelectedItem(38153)
            testDispatcher.scheduler.advanceUntilIdle()
            viewModel.searchDetailItem.test {
                val items=awaitItem()
                assertEquals("241", items.data?.GalleryNumber ?: "")

            }
        }
    }
}