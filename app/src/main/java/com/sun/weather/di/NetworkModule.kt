package com.sun.weather.di

import android.app.Application
import com.google.gson.Gson
import com.sun.weather.data.repository.source.remote.api.ApiService
import com.sun.weather.utils.Constant
import com.sun.weather.utils.Constant.BASE_API_SERVICE
import com.sun.weather.utils.Constant.BASE_RETROFIT
import com.sun.weather.utils.Constant.PRO_API_SERVICE
import com.sun.weather.utils.Constant.PRO_RETROFIT
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val NetworkModule =
    module {
        single { provideOkHttpCache(get()) }
        single { provideOkHttpClient(get()) }
        single<Retrofit>(named(BASE_RETROFIT)) { provideRetrofit(Constant.BASE_URL, get(), get()) }
        single<Retrofit>(named(PRO_RETROFIT)) { provideRetrofit(Constant.PRO_URL, get(), get()) }
        single<ApiService>(named(BASE_API_SERVICE)) { provideApiService(get(named(BASE_RETROFIT))) }
        single<ApiService>(named(PRO_API_SERVICE)) { provideApiService(get(named(PRO_RETROFIT))) }
    }

fun provideOkHttpCache(app: Application): Cache {
    val cacheSize: Long = NetWorkInstant.CACHE_SIZE
    return Cache(app.cacheDir, cacheSize)
}

fun provideOkHttpClient(cache: Cache): OkHttpClient {
    val httpClientBuilder = OkHttpClient.Builder()
    httpClientBuilder.cache(cache)

    httpClientBuilder.readTimeout(
        NetWorkInstant.READ_TIMEOUT,
        TimeUnit.SECONDS,
    )
    httpClientBuilder.writeTimeout(
        NetWorkInstant.WRITE_TIMEOUT,
        TimeUnit.SECONDS,
    )
    httpClientBuilder.connectTimeout(
        NetWorkInstant.CONNECT_TIMEOUT,
        TimeUnit.SECONDS,
    )
    return httpClientBuilder.build()
}

fun provideRetrofit(
    baseUrl: String,
    gson: Gson,
    okHttpClient: OkHttpClient,
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(okHttpClient)
        .build()
}

fun provideApiService(retrofit: Retrofit): ApiService {
    return retrofit.create(ApiService::class.java)
}

object NetWorkInstant {
    internal const val READ_TIMEOUT = 60L
    internal const val WRITE_TIMEOUT = 30L
    internal const val CONNECT_TIMEOUT = 60L
    internal const val CACHE_SIZE = 10 * 1024 * 1024L // 10MB
}
