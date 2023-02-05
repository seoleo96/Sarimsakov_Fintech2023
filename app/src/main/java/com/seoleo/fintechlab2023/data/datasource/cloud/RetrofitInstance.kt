package com.seoleo.fintechlab2023.data.datasource.cloud

import com.seoleo.fintechlab2023.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        private const val baseURL = "https://kinopoiskapiunofficial.tech/"
    }

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.HEADERS
        this.level = HttpLoggingInterceptor.Level.BASIC
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private fun apiKeyAsHeader(it: Interceptor.Chain) = it.proceed(
        it.request()
            .newBuilder()
            .addHeader("X-API-KEY", "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b")
            .build()
    )

    private val client: OkHttpClient = OkHttpClient.Builder()
        .apply {
            addInterceptor {
                apiKeyAsHeader(it)
            }
            if (BuildConfig.DEBUG) {
                addInterceptor(interceptor)
            }
        }
        .build()

    fun instance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }

    fun service(retrofit: Retrofit): FilmService {
        return retrofit.create(FilmService::class.java)
    }

}