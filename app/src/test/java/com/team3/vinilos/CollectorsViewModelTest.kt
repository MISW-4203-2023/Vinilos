package com.team3.vinilos

import com.team3.vinilos.fake.FakeDataSource
import com.team3.vinilos.fake.FakeNetworkCollectorsRepository
import com.team3.vinilos.rules.TestDispatcherRule
import com.team3.vinilos.viewModel.CollectorsUiState
import com.team3.vinilos.viewModel.CollectorsViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class CollectorsViewModelTest  {
    @get:Rule
    val testDispatcher = TestDispatcherRule()
    @Test
    fun collectorsViewModel_getCollectors_verifyCollectorsUiStateSuccess() =
        runTest {
            val collectorsViewModel = CollectorsViewModel(
                collectorsRepository = FakeNetworkCollectorsRepository()
            )
            assertEquals(
                CollectorsUiState.Success(FakeDataSource.listCollector),
                collectorsViewModel.collectorsUiState
            )
        }
}