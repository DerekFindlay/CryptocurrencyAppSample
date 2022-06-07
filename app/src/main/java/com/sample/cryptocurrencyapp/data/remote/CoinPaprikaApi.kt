package com.sample.cryptocurrencyapp.data.remote

import com.sample.cryptocurrencyapp.data.remote.dto.CryptoDetailDto
import com.sample.cryptocurrencyapp.data.remote.dto.CyptoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("/v1/coins")
    suspend fun getCryptos(): List<CyptoDto>


    /*
     * The below function is used so that a value can be passed to Retrofit to search on.
     * Here we can pass a parameter named coinId, which is a string, to the getCoinById() function.
     * The parameter will be substituted into the "coinId" space.
     */
    @GET("/v1/coins/{coinId}")
    suspend fun getCryptoById(@Path("coinId") coinId: String): CryptoDetailDto

}