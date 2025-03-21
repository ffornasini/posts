package com.francescofornasini.posts.domain.net

import com.francescofornasini.posts.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetModule {

    @Provides
    @Singleton
    @Named("log")
    fun logInterceptor(): Interceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    @Singleton
    @Named("libHttpClient")
    fun httpClient(@Named("log") logInterceptor: Interceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .build()

    @Provides
    @Singleton
    @Named("jsonConverterFactory")
    fun converterFactory(): Converter.Factory =
        Json.asConverterFactory("application/json".toMediaType())

    @Provides
    @Singleton
    @Named("libRetrofit")
    fun retrofit(
        @Named("httpClient") okHttpClient: OkHttpClient,
        @Named("jsonConverterFactory") converterFactory: Converter.Factory
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun api(retrofit: Retrofit): Api =
        retrofit.create(Api::class.java)
}