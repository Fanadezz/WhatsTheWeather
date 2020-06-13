package com.androidshowtime.whatstheweather.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

//base URL
private const val BASE_URL = "https://api.openweathermap.org/"


/*using MoshiBuilder to create MoshiObject i.e. the
JSON-String-to-Kotlin-Object converter*/

/*For Moshi's annotations to work properly with Kotlin
add the KotlinJsonAdapterFactory*/
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


//using Retrofit Builder to create Retrofit Object
private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .build()

//interface that defines how Retrofit talks to web server
interface WeatherInterface {

    // endpoint goes here
    @GET("data/2.5/weather")

//using @Query to add city i.e. q
    fun getWeatherPropertiesAsync(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Deferred<WeatherList>

    /*getWeatherPropertiesAsync() returns a Deferred Object which define a
    coroutine Job which has await() method*/

}


/*
define a public object called WeatherService to initialize the Retrofit service
object ensures only one instance of MarsAPI will be created*/

object WeatherService {

    val weatherService: WeatherInterface by lazy {

        retrofit.create(WeatherInterface::class.java)
    }
}

