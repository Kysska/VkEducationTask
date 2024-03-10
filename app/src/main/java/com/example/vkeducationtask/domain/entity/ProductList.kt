package com.example.vkeducationtask.domain.entity

data class ProductList(
    val products : List<Product>,
    val total:Int,
    val skip:Int,
    val limit:Int
)
