package com.sample.cryptocurrencyapp.domain.use_case.get_crypto

import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.data.remote.dto.toCryptoDetail
import com.sample.cryptocurrencyapp.domain.model.CryptoDetail
import com.sample.cryptocurrencyapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetCryptoUseCase @Inject constructor(
    private val repository: CryptoRepository
) {

    operator fun invoke(cyptoId: String): Flow<Resource<CryptoDetail>> = flow {
        try {
            emit(Resource.Loading())
            val crypto = repository.getCryptoById(cryptoId = cyptoId).toCryptoDetail()
            emit(Resource.Success<CryptoDetail>(crypto))
        } catch (e: IOException) {
            emit(Resource.Error<CryptoDetail>(e.localizedMessage ?: "An unexpected error occurred."))
        } catch (e: HttpException) {
            emit(Resource.Error<CryptoDetail>("Please check the internet connection."))
        }
    }
}