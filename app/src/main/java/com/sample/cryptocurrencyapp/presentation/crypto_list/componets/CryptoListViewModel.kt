package com.sample.cryptocurrencyapp.presentation.crypto_list.componets

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.domain.use_case.get_cryptos.GetCryptosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CryptoListViewModel @Inject constructor(
    private val getCryptosUseCase: GetCryptosUseCase
) : ViewModel() {

    private val _state = mutableStateOf(CryptoListState())
    val state: State<CryptoListState> = _state

    init {
        getCryptos()
    }

    private fun getCryptos() {
        getCryptosUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.value = _state.value.copy(crypto = result.data ?: emptyList(), isLoading = false)
                }
                is Resource.Error -> {
                    _state.value =
                        _state.value.copy(error = result.message ?: "An unexpected error occurred.")
                }
                is Resource.Loading -> {
                    _state.value = _state.value.copy(isLoading = true)
                }
            }

        }.launchIn(viewModelScope)
    }
}