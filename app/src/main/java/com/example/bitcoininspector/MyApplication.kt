package com.example.bitcoininspector

import android.app.Application
import com.example.bitcoininspector.di.AppComponent
import com.example.bitcoininspector.di.DaggerAppComponent

open class MyApplication : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(applicationContext)
    }
}