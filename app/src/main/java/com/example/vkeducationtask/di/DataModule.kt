package com.example.vkeducationtask.di

import com.example.vkeducationtask.data.ProductRepositoryImpl
import com.example.vkeducationtask.data.api.MainApi
import com.example.vkeducationtask.domain.ProductRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {
    @Provides
    @Singleton
    fun provideProductRepositoryImpl(mainApi: MainApi) : ProductRepository{
        return ProductRepositoryImpl(mainApi)
    }
}