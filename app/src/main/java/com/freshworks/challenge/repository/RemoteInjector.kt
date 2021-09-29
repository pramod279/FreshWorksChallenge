package com.freshworks.challenge.repository

import com.freshworks.challenge.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @Author: Pramod Selvaraj
 * @Date: 29.09.2021
 */
object RemoteInjector {
    private const val HEADER_API_KEY = "api_key"

    /*Fetch Retrofit Client*/
    fun injectGiphyApiService(retrofit: Retrofit = getRetrofit()): GiphyApiService {
        return retrofit.create(GiphyApiService::class.java)
    }

    /*Fetch Retrofit Client*/
    private fun getRetrofit(okHttpClient: OkHttpClient = getOkHttpClient()): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    /*Attaching Interceptors To Retrofit Calls*/
    private fun getOkHttpClient(
        okHttpLogger: HttpLoggingInterceptor = getHttpLogger(),
        okHttpAuthInterceptor: Interceptor = getAuthInterceptor(),
        okHttpNetworkInterceptor: Interceptor = getOkHttpNetworkInterceptor()
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(okHttpLogger)
            .addInterceptor(okHttpAuthInterceptor)
            .addInterceptor(okHttpNetworkInterceptor)
            .build()
    }

    /*Logging Interceptor*/
    private fun getHttpLogger(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    /*Attach Auth Interceptor For Common Query Parameters*/
    private fun getAuthInterceptor(): Interceptor {
        return Interceptor { chain ->
            var req = chain.request()
            req = req.newBuilder().url(
                req.url.newBuilder().addQueryParameter(HEADER_API_KEY, BuildConfig.API_KEY).build()
            ).build()
            chain.proceed(req)
        }
    }

    /*Attach Request Headers*/
    private fun getOkHttpNetworkInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newRequest =
                chain.request().newBuilder().addHeader(HEADER_API_KEY, BuildConfig.API_KEY).build()
            chain.proceed(newRequest)
        }
    }
}