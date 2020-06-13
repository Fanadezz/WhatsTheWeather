package com.androidshowtime.whatstheweather.network

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


/*@Parcelize is an annotation provided by Kotlin Android Extensions
that will automatically generate the serialization implementation
for your custom Parcelable type at compile time!*/

//data class - constructor takes main and description Strings
@Parcelize
data class Weather(val main: String, val description: String) :
    Parcelable


//the JSON Object returns as a list, here we wrap the Weather class as a list to match the returned object
@Parcelize
data class WeatherList(

    val weather: List<Weather>

) : Parcelable


