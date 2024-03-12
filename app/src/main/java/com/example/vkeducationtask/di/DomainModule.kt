package com.example.vkeducationtask.di

import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.usecase.GetCategories
import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import com.example.vkeducationtask.domain.usecase.GetSearchProductsPaging
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

    @Singleton
    @Provides
    fun provideGetCategories(repository: ProductRepository) :GetCategories{
        return GetCategories(repository)
    }

    @Singleton
    @Provides
    fun provideGetSearchProductsPaging(repository: ProductRepository) :GetSearchProductsPaging{
        return GetSearchProductsPaging(repository)
    }
}