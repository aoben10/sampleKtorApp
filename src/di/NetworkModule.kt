package com.theobencode.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.theobencode.logger
import com.theobencode.network.DummyAPIService
import com.theobencode.network.OutlookOAuthAPIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

const val BASE_URL = "http://dummy.restapiexample.com/api/v1/"
const val OUTLOOK_BASE_URL = "https://login.microsoftonline.com"

val networkModule = module {
    single { createMoshiAdapter() }
    single { createOkHttpClient() }
    single { createWebService<DummyAPIService>(get(), get(), BASE_URL) }
    single { createWebService<OutlookOAuthAPIService>(get(), get(), OUTLOOK_BASE_URL) }
}

inline fun <reified T> createWebService(moshi: Moshi, okHttpClient: OkHttpClient, url: String): T {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build().create()
}

fun createOkHttpClient(): OkHttpClient {
    // TODO: Only log for debug
    val okHttpLogger = HttpLoggingInterceptor.Logger { logger.debug(it) }
    val loggingInterceptor = HttpLoggingInterceptor(okHttpLogger).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    return OkHttpClient.Builder().apply { addInterceptor(loggingInterceptor) }.build()
}

fun createMoshiAdapter(): Moshi {
    return Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
}