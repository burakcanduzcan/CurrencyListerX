package com.burakcanduzcan.currencylisterx.di

import android.content.Context
import android.os.Environment
import com.burakcanduzcan.currencylisterx.data.remote.CoinGeckoService
import com.prof.rssparser.Parser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        val cache = Cache(Environment.getDownloadCacheDirectory(), 10 * 1024 * 1024)
        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .cache(cache)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideCoinGeckoService(client: OkHttpClient): CoinGeckoService {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinGeckoService::class.java)
    }

    @Singleton
    @Provides
    fun provideParser(@ApplicationContext context: Context): Parser {
        return Parser.Builder()
            .context(context)
            .cacheExpirationMillis(24L * 60L * 60L * 1000L)
            .build()
    }

    //...
}