package com.example.vkeducationtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkeducationtask.domain.usecase.GetPagingProducts

class MainViewModelFactory(

    private val getPagingProducts: GetPagingProducts
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getPagingProducts) as T
    }
}