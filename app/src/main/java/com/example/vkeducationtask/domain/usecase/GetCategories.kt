package com.example.vkeducationtask.domain.usecase

import com.example.vkeducationtask.domain.ProductRepository

class GetCategories(private val repository: ProductRepository) {

    suspend fun getCategories() : List<String>{
        return repository.getCategories()
    }

}