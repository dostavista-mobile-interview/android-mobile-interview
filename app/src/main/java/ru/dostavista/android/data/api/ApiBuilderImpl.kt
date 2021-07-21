package ru.dostavista.android.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiBuilderImpl: ApiBuilder {

    override fun <T> createApi(api: Class<T>): T {
        val baseUrl = "https://externalwebhooks.dostavista.net/"

        val httpClientBuilder = OkHttpClient.Builder()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(api)
    }

}
