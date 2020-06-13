package com.androidshowtime.whatstheweather.viewModel

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.androidshowtime.whatstheweather.network.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MyViewModel : ViewModel(), Observable {

    //mainDesc for internal use
    private val _mainDesc = MutableLiveData<String>()

    //LiveData for exposing mainDesc to the outside classes
    val mainDesc: LiveData<String>
        get() = _mainDesc

    //CoroutineScope and Job initialization - update happening on the UI Thread
    val job = Job()
    private val coroutineScope = CoroutineScope(Dispatchers.Main + job)

    //variable for the 2-way Data Binding from the user
    @Bindable
    val editTextContent = MutableLiveData<String>()

    //variable for the 2-way Data Binding from the model
    private val _displayedEditTextContent = MutableLiveData<String>()


    //getting response from internet
    private fun getWeatherDetails() {
        coroutineScope.launch {
            val city = _displayedEditTextContent.value!!

            val getDeferredProperties = WeatherService.weatherService.getWeatherPropertiesAsync(
                city, "1d6334d64c0fa342bdbd20e6952fd8ba"
            )

            try {
                val listResult = getDeferredProperties.await()

                val weather = listResult.weather[0]


                //setting value of mainDesc on success
                _mainDesc.value = "${weather.main}\n${weather.description}"


            } catch (e: Exception) {
                //setting value of mainDesc on failure
                _mainDesc.value = "Sorry, please try again"
            }
        }

    }


    fun onClick() {
        if (editTextContent.value.equals(null)) {
//setting a default value to avoid null pointer exception
            editTextContent.value = "Tehran"

        }

//on button click set the value from the model to the edittext
        _displayedEditTextContent.value = editTextContent.value
        getWeatherDetails()

    }


    override fun onCleared() {
        super.onCleared()
//cancel the job when viewModel is destoyed
        job.cancel()
    }


    //implementations for Observable responsible for 2-Way Data Binding
    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }
}