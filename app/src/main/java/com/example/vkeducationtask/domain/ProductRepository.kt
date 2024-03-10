package com.example.vkeducationtask.domain

import androidx.paging.PagingData
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun getProductsPaging() : Flow<PagingData<Product>>
}