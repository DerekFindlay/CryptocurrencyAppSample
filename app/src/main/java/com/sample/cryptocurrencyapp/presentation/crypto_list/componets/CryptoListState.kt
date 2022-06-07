package com.sample.cryptocurrencyapp.presentation.crypto_list.componets

import com.sample.cryptocurrencyapp.domain.model.Crypto

data class CryptoListState(
    val isLoading: Boolean = false,
    val crypto: List<Crypto> = emptyList(),
    val error: String = ""
)
