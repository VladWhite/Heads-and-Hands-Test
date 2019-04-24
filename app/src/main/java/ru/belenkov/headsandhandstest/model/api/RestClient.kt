package ru.belenkov.headsandhandstest.model.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.belenkov.headsandhandstest.util.BASE_URL
import ru.belenkov.headsandhandstest.util.NETWORK_TIMEOUT
import java.util.*
import java.util.concurrent.TimeUnit

object RestClient {

    private lateinit var interceptor: HttpLoggingInterceptor

    private lateinit var okHttpClient: OkHttpClient

    private lateinit var retrofit: Retrofit

    val client: Retrofit
        get() {
            interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectionSpecs(
                    Arrays.asList(
                        ConnectionSpec.MODERN_TLS,
                        ConnectionSpec.COMPATIBLE_TLS
                    )
                )
                .followRedirects(true)
                .followSslRedirects(true)
                .retryOnConnectionFailure(true)
                .connectTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(NETWORK_TIMEOUT, TimeUnit.SECONDS)
                .cache(null)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .client(okHttpClient)
                .build()

            return retrofit
        }
}