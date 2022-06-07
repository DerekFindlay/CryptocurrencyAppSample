package com.sample.cryptocurrencyapp.presentation.crypto_detail

import com.sample.cryptocurrencyapp.domain.model.CryptoDetail

data class CryptoDetailState(
    val isLoading: Boolean = false,
    val crypto: CryptoDetail? = null,
    val error: String = ""
)
