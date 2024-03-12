package com.example.vkeducationtask.domain.usecase

import androidx.paging.PagingData
import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow

class GetSearchProductsPaging(private val repository: ProductRepository) {
    fun getSearchPagingProducts(query: String?) : Flow<PagingData<Product>> {
        return repository.getSearchProductsPaging(query)
    }
}