package com.mktiti.reportstream.network

import com.google.gson.GsonBuilder
import com.mktiti.reportstream.model.LanguagesDeserializer
import com.mktiti.reportstream.model.LanguagesResponse
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class NetModule {

    companion object {
        val BASE_URL = "https://api.currentsapi.services/v1/"

        val API_KEY = "prrRePkM5V5yen1X03O5_sFN7cCUa1KPDJIS2uf1WB2eevBz"
    }

    @Provides
    @Singleton
    fun httpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor).build()
    }

    private fun gsonFactory(): GsonConverterFactory {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(LanguagesResponse::class.java, LanguagesDeserializer)
        return GsonConverterFactory.create(gsonBuilder.create())
    }

    @Provides
    @Singleton
    fun retrofit(client: OkHttpClient = httpClient()) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(gsonFactory())
        .build()

}