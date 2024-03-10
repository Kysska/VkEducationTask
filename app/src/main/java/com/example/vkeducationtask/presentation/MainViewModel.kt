package com.example.vkeducationtask.presentation


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vkeducationtask.domain.entity.Product
import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import kotlinx.coroutines.flow.Flow

class MainViewModel(
    private val getPagingProducts : GetPagingProducts
) : ViewModel() {

    val products: Flow<PagingData<Product>> = getPagingProducts.getPagingProducts().cachedIn(viewModelScope)

}