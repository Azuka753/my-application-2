package com.azurebear.myapplication2.network

import com.azurebear.myapplication2.model.CurrentWeather
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c41e5bdf97dd40808224368a154d9d8a"
//https://devapi.qweather.com/v7/weather/now?key=c41e5bdf97dd40808224368a154d9d8a&location=101200101

interface QWeatherApiService {
    @GET("now")
    suspend fun getCurrentWeather(
        @Query("location") location: String = "101200101"
    ): CurrentWeather

    companion object{
        operator fun invoke(
            connectivityInterceptor: ConnectivityInterceptor
        ): QWeatherApiService{
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(connectivityInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("https://devapi.qweather.com/v7/weather/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(QWeatherApiService::class.java)
        }
    }
}