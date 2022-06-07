package com.sample.cryptocurrencyapp.domain.repository

import com.sample.cryptocurrencyapp.data.remote.dto.CryptoDetailDto
import com.sample.cryptocurrencyapp.data.remote.dto.CyptoDto

interface CryptoRepository {

    suspend fun getCrypto(): List<CyptoDto>

    suspend fun getCryptoById(cryptoId: String): CryptoDetailDto
}