package com.example.vkeducationtask.di

import com.example.vkeducationtask.presentation.MainActivity
import com.example.vkeducationtask.presentation.ProductsListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class, DataModule::class, DomainModule::class, AppModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)

    fun inject(fragment: ProductsListFragment)

}