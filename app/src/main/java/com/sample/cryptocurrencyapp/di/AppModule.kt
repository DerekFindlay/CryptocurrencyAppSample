package com.sample.cryptocurrencyapp.di

import com.sample.cryptocurrencyapp.common.Constants.BASE_URL
import com.sample.cryptocurrencyapp.data.remote.CoinPaprikaApi
import com.sample.cryptocurrencyapp.BuildConfig
import com.sample.cryptocurrencyapp.data.repository.CryptoRepositoryImp
import com.sample.cryptocurrencyapp.domain.repository.CryptoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
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
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            builder.addInterceptor(loggingInterceptor)
        }

        return builder.build()
    }

    @Provides
    @Singleton //This makes sure there is only a single instance of this function.
    fun provideCoinPaprikaApi(okHttpClient: OkHttpClient) : CoinPaprikaApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
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