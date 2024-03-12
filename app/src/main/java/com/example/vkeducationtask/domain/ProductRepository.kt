package com.example.vkeducationtask.domain

import androidx.paging.PagingData
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow


interface ProductRepository {
    fun getProductsPaging(category: String?) : Flow<PagingData<Product>>
    fun getSearchProductsPaging(query : String?) : Flow<PagingData<Product>>
    suspend fun getCategories() : List<String>
}