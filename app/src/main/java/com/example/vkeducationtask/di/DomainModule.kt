package com.example.vkeducationtask.di

import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Singleton
    @Provides
    fun provideGetPagingProducts(repository: ProductRepository) :GetPagingProducts{
        return GetPagingProducts(repository)
    }
}