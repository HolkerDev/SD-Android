package com.holker.smart.di.modules

import com.holker.smart.data.repository.SmartAdApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class RepositoryModule {
    private val baseUrl = "http://10.0.2.2:8000" //TODO : Fix for local machine
    @Provides
    @Singleton
    fun providesApiService(): SmartAdApiService {
        val retrofit: Retrofit
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build();

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(SmartAdApiService::class.java)
    }
}