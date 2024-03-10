package com.example.vkeducationtask.di

import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import com.example.vkeducationtask.presentation.MainViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    @Singleton
    fun provideMainViewModelFactory(
        getPagingProducts: GetPagingProducts
    ): MainViewModelFactory {
        return MainViewModelFactory(getPagingProducts)
    }

}