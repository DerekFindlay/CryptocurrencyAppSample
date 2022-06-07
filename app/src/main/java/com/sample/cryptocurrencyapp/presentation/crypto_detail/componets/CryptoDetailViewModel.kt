package com.sample.cryptocurrencyapp.presentation.crypto_detail.componets

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.cryptocurrencyapp.common.Constants.PARMA_CRYPTO_ID
import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.domain.use_case.get_crypto.GetCryptoUseCase
import com.sample.cryptocurrencyapp.presentation.crypto_detail.CryptoDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val getCryptoDetailUseCase: GetCryptoUseCase,
    private val savedStateHandle: SavedStateHandle //See Notes Below
) : ViewModel() {
    val _state = mutableStateOf(CryptoDetailState())
    val state: State<CryptoDetailState> = _state

    init {
        savedStateHandle.get<String>(PARMA_CRYPTO_ID)?.let { coinId ->
            getCrypto(coinId)
        }
    }


    private fun getCrypto(coinId: String) {
        //This return a flow, so onEach may be called for each of the flow items.
        getCryptoDetailUseCase(coinId).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = CryptoDetailState(crypto = result.data)
                }
                is Resource.Error -> {
                    _state.value = CryptoDetailState(
                        error = result.message ?: "An unexpected error occurred."
                    )
                }
                is Resource.Loading -> {
                    _state.value = CryptoDetailState(
                        isLoading = true
                    )
                }
            }
        }.launchIn(viewModelScope)
    }
}


/*
 * The savedStateHandle is used to retrieve a value that we previously saved to the SavedState.
 * We get the stored value and then pass it as a parameter to the getCrypto() function.
 */

