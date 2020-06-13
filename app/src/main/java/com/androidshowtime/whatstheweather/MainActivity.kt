package com.androidshowtime.whatstheweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.androidshowtime.whatstheweather.databinding.ActivityMainBinding
import com.androidshowtime.whatstheweather.viewModel.MyViewModel
import com.androidshowtime.whatstheweather.viewModel.ViewModelFactoryClass
import timber.log.Timber


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())


        //getting the factory to initialize viewModel
        val factory = ViewModelFactoryClass()

        //initializing viewModel from ViewModelFactory
        val viewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)

        //binding object
        val binding = ActivityMainBinding.inflate(layoutInflater)

        // Allowing DataBinding to Observe LiveData with the lifecycle of this Activity
        binding.lifecycleOwner = this

        /*giving the binding access to the OverviewViewModel - because we have set the
         lifecycle Owner any LiveData used in data binding will automatically be
         observed for any changes and the UI will be updated accordingly*/
        binding.viewModel = viewModel



        setContentView(binding.root)
    }

}