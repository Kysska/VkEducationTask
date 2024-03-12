package com.example.vkeducationtask.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.vkeducationtask.data.api.MainApi
import com.example.vkeducationtask.domain.entity.Product

class SearchProductPagingImpl(private val mainApi: MainApi, private val query: String? = null) : PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val skip = params.key ?: 0
            val limit = params.loadSize
            val response = mainApi.searchProducts(query, skip, limit)
            val products = response.body()?.products ?: emptyList()
            val nextKey = if (products.isEmpty()) null else products.size + skip + 1
            LoadResult.Page(
                data = products,
                prevKey = null,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}