package com.sample.cryptocurrencyapp.di

import com.sample.cryptocurrencyapp.common.Constants.BASE_URL
import com.sample.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.sample.cryptocurrencyapp.data.repository.CryptoRepositoryImp
import com.sample.cryptocurrencyapp.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*
 * This is an important class as it show how Dagger Hilt knows how to create the CoinPaprikaApi
 * and also how it is able to provide the CoinRepositoryImpl. This ability to provide the repository
 * is how we are able to avoid using concretion in the app.
 */
@Module
@InstallIn(SingletonComponent::class) //This makes certain that the object lives as long as the application.
object AppModule {

    @Provides
    @Singleton //This makes sure there is only a single instance of this function.
    fun provideCoinPaprikaApi() : CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java) //This line defines the API interface that we are creating.
    }

    @Provides
    @Singleton
    fun provideCoinRepository(api : CoinPaprikaApi) : CryptoRepository {
        return CryptoRepositoryImp(api)
    }

}