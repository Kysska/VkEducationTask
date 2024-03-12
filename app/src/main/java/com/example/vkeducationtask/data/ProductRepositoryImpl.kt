package com.example.vkeducationtask.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vkeducationtask.data.api.MainApi
import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val mainApi : MainApi) : ProductRepository{

    override fun getProductsPaging(category: String?): Flow<PagingData<Product>> {

        val pagingSource = ProductPagingImpl(mainApi, category)
        val pagingData = Pager(
            config = PagingConfig(pageSize = LIMIT, initialLoadSize = 20),
            pagingSourceFactory = { pagingSource }
        ).flow
        return pagingData

    }

    override suspend fun getCategories(): List<String> {
        try {
            return mainApi.getCategories().body() ?: emptyList()
        }
        catch (e : Exception){
            return emptyList()
        }
    }

    companion object{
        private const val LIMIT = 20
    }

}