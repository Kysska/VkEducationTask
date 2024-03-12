package com.example.vkeducationtask.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.vkeducationtask.domain.usecase.GetCategories
import com.example.vkeducationtask.domain.usecase.GetPagingProducts

class MainViewModelFactory(

    private val getPagingProducts: GetPagingProducts,
    private val getCategories: GetCategories
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getPagingProducts, getCategories) as T
    }
}