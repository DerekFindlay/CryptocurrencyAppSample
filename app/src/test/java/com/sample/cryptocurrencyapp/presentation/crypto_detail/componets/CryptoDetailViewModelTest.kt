package com.sample.cryptocurrencyapp.presentation.crypto_detail.componets

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.sample.cryptocurrencyapp.common.Constants
import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.domain.model.CryptoDetail
import com.sample.cryptocurrencyapp.domain.model.TeamMember
import com.sample.cryptocurrencyapp.domain.use_case.get_crypto.GetCryptoUseCase
import com.sample.cryptocurrencyapp.presentation.crypto_detail.CryptoDetailState
import com.sample.cryptocurrencyapp.presentation.util.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class CryptoDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: CryptoDetailViewModel
    private val getCryptoUseCase: GetCryptoUseCase = mockk()
    private lateinit var savedStateHandle: SavedStateHandle

    @Test
    fun `getCryptoDetail_success_updatesStateWithDetailAndNotLoading`() = runTest {
        val coinId = "btc-bitcoin"
        savedStateHandle = SavedStateHandle(mapOf(Constants.PARMA_CRYPTO_ID to coinId))
        val mockCryptoDetail = CryptoDetail(
            cryptoId = "btc-bitcoin",
            name = "Bitcoin",
            description = "Description",
            symbol = "BTC",
            rank = 1,
            isActive = true,
            tags = listOf("tag1", "tag2"),
            team = listOf(TeamMember(id = "1", name = "Satoshi", position = "Founder"))
        )
        every { getCryptoUseCase(coinId) } returns flowOf(Resource.Success(mockCryptoDetail))

        viewModel = CryptoDetailViewModel(getCryptoUseCase, savedStateHandle)

        viewModel.state.test {
            // Initial state might be loading = true if getCrypto is called in init
            var initialState = awaitItem() 
            if(!initialState.isLoading && initialState.crypto == null) { // if not loading initially, await next loading
                 initialState = awaitItem()
            }
            assertEquals(CryptoDetailState(isLoading = true, error = "", crypto = null), initialState)
            
            val successState = awaitItem()
            assertEquals(CryptoDetailState(isLoading = false, crypto = mockCryptoDetail, error = ""), successState)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCryptoDetail_error_updatesStateWithErrorAndNotLoading`() = runTest {
        val coinId = "btc-bitcoin"
        val errorMessage = "Network Error"
        savedStateHandle = SavedStateHandle(mapOf(Constants.PARMA_CRYPTO_ID to coinId))
        every { getCryptoUseCase(coinId) } returns flowOf(Resource.Error(errorMessage))

        viewModel = CryptoDetailViewModel(getCryptoUseCase, savedStateHandle)

        viewModel.state.test {
            var initialState = awaitItem()
            if(!initialState.isLoading && initialState.error == "") {
                initialState = awaitItem()
            }
            assertEquals(CryptoDetailState(isLoading = true, error = "", crypto = null), initialState)

            val errorState = awaitItem()
            assertEquals(CryptoDetailState(isLoading = false, error = errorMessage), errorState)
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `getCryptoDetail_loading_updatesStateWithLoading`() = runTest {
        val coinId = "btc-bitcoin"
        savedStateHandle = SavedStateHandle(mapOf(Constants.PARMA_CRYPTO_ID to coinId))
        // Use a flow that emits Loading then never completes to stay in loading
        every { getCryptoUseCase(coinId) } returns flow { emit(Resource.Loading()) }


        viewModel = CryptoDetailViewModel(getCryptoUseCase, savedStateHandle)

        viewModel.state.test {
            val loadingState = awaitItem() // This will be the initial state (isLoading=false)
            if(loadingState.isLoading == false) { // if the init getCrypto is not yet reflected
                 assertEquals(CryptoDetailState(isLoading = true, error = "", crypto = null), awaitItem())
            } else { // if the init getCrypto is already reflected
                 assertEquals(CryptoDetailState(isLoading = true, error = "", crypto = null), loadingState)
            }

            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `init_noCoinId_stateRemainsInitialAndUseCaseNotCalled`() = runTest {
        savedStateHandle = SavedStateHandle() // No coinId

        viewModel = CryptoDetailViewModel(getCryptoUseCase, savedStateHandle)

        assertEquals(CryptoDetailState(), viewModel.state.value) // Assert initial state

        // Verify that getCryptoUseCase was not called
        verify(exactly = 0) { getCryptoUseCase(any()) }
    }
}
