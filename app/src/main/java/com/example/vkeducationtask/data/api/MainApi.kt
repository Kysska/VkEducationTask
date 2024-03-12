package com.example.vkeducationtask.data.api

import com.example.vkeducationtask.domain.entity.ProductList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MainApi {
    @GET("/products")
    suspend fun getProductsPaging(@Query("skip") skip:Int, @Query("limit") limit:Int) : Response<ProductList>

    @GET("/products/category/{category}")
    suspend fun getProductsPagingWithCategory(@Path("category") category:String, @Query("skip") skip: Int, @Query("limit") limit: Int) : Response<ProductList>

    @GET("/products/categories")
    suspend fun getCategories() : Response<List<String>>

    @GET("/products/search")
    suspend fun searchProducts(@Query("q") query: String?, @Query("skip") skip: Int, @Query("limit") limit: Int): Response<ProductList>

}