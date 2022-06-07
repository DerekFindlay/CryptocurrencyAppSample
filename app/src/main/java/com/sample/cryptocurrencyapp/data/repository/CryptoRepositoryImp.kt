package com.sample.cryptocurrencyapp.data.repository

import com.sample.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.sample.cryptocurrencyapp.data.remote.dto.CryptoDetailDto
import com.sample.cryptocurrencyapp.data.remote.dto.CyptoDto
import com.sample.cryptocurrencyapp.domain.repository.CryptoRepository
import javax.inject.Inject

class CryptoRepositoryImp @Inject constructor(
    private val api: CoinPaprikaApi
) : CryptoRepository {
    override suspend fun getCrypto(): List<CyptoDto> {
        return api.getCryptos()
    }

    override suspend fun getCryptoById(cryptoId: String): CryptoDetailDto {
        return api.getCryptoById(cryptoId)
    }
}