package com.example.vkeducationtask.data


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.vkeducationtask.data.api.MainApi
import com.example.vkeducationtask.domain.ProductRepository
import com.example.vkeducationtask.domain.entity.Product
import kotlinx.coroutines.flow.Flow

class ProductRepositoryImpl(private val mainApi : MainApi) : ProductRepository{

    override fun getProductsPaging(): Flow<PagingData<Product>> {

        val pagingSource = ProductPagingImpl(mainApi)
        val pagingData = Pager(
            config = PagingConfig(pageSize = LIMIT, initialLoadSize = 20),
            pagingSourceFactory = { pagingSource }
        ).flow
        return pagingData

    }

    companion object{
        private const val LIMIT = 20
    }

}