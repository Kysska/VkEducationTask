package com.example.vkeducationtask.data

import android.util.Log
import androidx.paging.LoadType
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vkeducationtask.data.api.MainApi
import com.example.vkeducationtask.domain.entity.Product

class ProductPagingImpl(private val mainApi : MainApi): PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val skip = params.key ?: 0
            val limit = params.loadSize
            val response = mainApi.getProductsPaging(skip = skip, limit = limit)
            val products = response.body()?.products ?: emptyList()
            val nextKey = if(products.isEmpty()) null else products.size.plus(skip).plus(1)
//            val prevKey = if(skip == 0) null else products.size.minus(limit)
            LoadResult.Page(
                products, nextKey = nextKey, prevKey = null
            )
        } catch (e : Exception){
            LoadResult.Error(e)
        }
    }
}