package com.example.vkeducationtask.di

import com.example.vkeducationtask.presentation.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DataModule::class, DomainModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}