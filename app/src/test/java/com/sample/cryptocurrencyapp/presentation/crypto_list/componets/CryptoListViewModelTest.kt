package com.sample.cryptocurrencyapp.presentation.crypto_list.componets

import app.cash.turbine.test
import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.domain.model.Crypto
import com.sample.cryptocurrencyapp.domain.use_case.get_cryptos.GetCryptosUseCase
import com.sample.cryptocurrencyapp.presentation.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CryptoListViewModel
    private val getCryptosUseCase: GetCryptosUseCase = mockk()

    @Test
    fun `getCryptos_success_updatesStateWithCryptoListAndNotLoading`() = runTest {
        val mockCryptos = listOf(
            Crypto(id = "1", isActive = true, name = "Bitcoin", rank = 1, symbol = "BTC"),
            Crypto(id = "2", isActive = true, name = "Ethereum", rank = 2, symbol = "ETH")
        )
        every { getCryptosUseCase() } returns flowOf(Resource.Success(mockCryptos))

        viewModel = CryptoListViewModel(getCryptosUseCase)

        viewModel.state.test {
            // Initial state or loading state if getCryptos is called in init
            var emittedItem = awaitItem()
            // If getCryptos is called in init, it might directly go to success or pass through loading first.
            // We are interested in the final success state and the loading state before it.

            // First, expect loading state because of init
            assertEquals(CryptoListState(isLoading = true, error = ""), emittedItem)

            // Then, expect success state
            emittedItem = awaitItem()
            assertEquals(CryptoListState(crypto = mockCryptos, isLoading = false, error = ""), emittedItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCryptos_error_updatesStateWithErrorAndNotLoading`() = runTest {
        val errorMessage = "Network Error"
        every { getCryptosUseCase() } returns flowOf(Resource.Error(errorMessage))

        viewModel = CryptoListViewModel(getCryptosUseCase)

        viewModel.state.test {
             // Initial state or loading state
            var emittedItem = awaitItem()
            assertEquals(CryptoListState(isLoading = true, error = ""), emittedItem)
            
            emittedItem = awaitItem()
            assertEquals(CryptoListState(error = errorMessage, isLoading = false), emittedItem)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCryptos_loading_updatesStateWithLoading`() = runTest {
        // Simulate a flow that emits Loading then Success, to check the Loading state explicitly
        val mockCryptos = listOf(Crypto(id = "1", isActive = true, name = "Bitcoin", rank = 1, symbol = "BTC"))
        every { getCryptosUseCase() } returns flowOf(Resource.Loading(), Resource.Success(mockCryptos))

        viewModel = CryptoListViewModel(getCryptosUseCase)

        viewModel.state.test {
            val firstEmission = awaitItem()
            assertTrue(firstEmission.isLoading)
            assertEquals("", firstEmission.error)

            // Consume the success state to allow the test to finish
            awaitItem() 
            cancelAndConsumeRemainingEvents()
        }
    }
}
