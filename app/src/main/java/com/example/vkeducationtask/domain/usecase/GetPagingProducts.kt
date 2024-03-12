package com.example.vkeducationtask.domain.usecase


import androidx.paging.PagingData
import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow

class GetPagingProducts(private val repository: ProductRepository) {
    fun getPagingProducts(category: String?) : Flow<PagingData<Product>> {
         return repository.getProductsPaging(category)
    }
}