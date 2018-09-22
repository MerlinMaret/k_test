package com.kreactive.technicaltest.dependencyinjection

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapterFactory
import com.kreactive.technicaltest.BuildConfig
import com.kreactive.technicaltest.api.OMDbService
import com.kreactive.technicaltest.api.PostProcessingEnabler
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = Kodein.Module("network") {

    val wsUrl = "http://www.omdbapi.com"

    //region TAGs

    val TAG_WS_URL = "WS_URL"
    val TAG_HTTP_INTERCEPTOR = "TAG_HTTP_INTERCEPTOR"
    val TAG_ERROR_INTERCEPTOR = "TAG_ERROR_INTERCEPTOR"

    //endregion

    bind<Gson>() with singleton {
        GsonBuilder()
                .registerTypeAdapterFactory(instance<TypeAdapterFactory>())
                .create()
    }

    bind<TypeAdapterFactory>() with singleton {
        PostProcessingEnabler()
    }

    bind<Interceptor>(TAG_HTTP_INTERCEPTOR) with singleton {
        val logging = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            logging.setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    bind<OkHttpClient>() with singleton {
        OkHttpClient.Builder()
                .addInterceptor(instance<Interceptor>(TAG_HTTP_INTERCEPTOR))
                .build()

    }

    bind<Converter.Factory>() with singleton {
        GsonConverterFactory.create(instance<Gson>())
    }

    bind<CallAdapter.Factory>() with singleton {
        RxJavaCallAdapterFactory.create()
    }

    bind<Retrofit>() with singleton {
        val url = instance<String>(TAG_WS_URL)
        Retrofit.Builder()
                .client(instance<OkHttpClient>())
                .baseUrl(url)
                .addConverterFactory(instance<Converter.Factory>())
                .addCallAdapterFactory(instance<CallAdapter.Factory>())
                .build()
    }

    bind<OMDbService>() with singleton {
        val retrofit = instance<Retrofit>()
        retrofit.create(OMDbService::class.java)
    }

    bind<String>(TAG_WS_URL) with singleton {
        wsUrl
    }
}