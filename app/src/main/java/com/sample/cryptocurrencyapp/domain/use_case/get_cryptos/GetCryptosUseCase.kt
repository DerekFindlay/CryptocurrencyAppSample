package com.sample.cryptocurrencyapp.domain.use_case.get_cryptos

import com.sample.cryptocurrencyapp.common.Resource
import com.sample.cryptocurrencyapp.data.remote.dto.toCrypto
import com.sample.cryptocurrencyapp.domain.model.Crypto
import com.sample.cryptocurrencyapp.domain.repository.CryptoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


//NOTE: We do not inject the implementation as this would mean we are using concreation.
class GetCryptosUseCase @Inject constructor(
    private val repository: CryptoRepository
) {

    //Here I override the invoke function so that I can access the GetCoinUseCase like a function.
    operator fun invoke() : Flow<Resource<List<Crypto>>>  = flow {
        try {
            emit(Resource.Loading())
            val coins = repository.getCrypto()
            emit(Resource.Success<List<Crypto>>(
                coins.map { it.toCrypto() }
            ))
        } catch (e: HttpException) {
            emit(Resource.Error<List<Crypto>>(message = e.localizedMessage ?: "There has been a HttpException"))
        } catch (e: IOException) {
            emit(Resource.Error<List<Crypto>>(message = "Connection error: Please check internet connection."))
        }

    }

}
