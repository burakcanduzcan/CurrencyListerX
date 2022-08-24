package com.burakcanduzcan.currencylisterx.di

import android.content.Context
import com.burakcanduzcan.currencylisterx.data.remote.CoinGeckoService
import com.prof.rssparser.Parser
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideCoinGeckoService(): CoinGeckoService {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com")
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