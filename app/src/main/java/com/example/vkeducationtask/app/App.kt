package com.example.vkeducationtask.app

import android.app.Application
import com.example.vkeducationtask.di.AppComponent
import com.example.vkeducationtask.di.DaggerAppComponent

class App : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().build()
    }
}