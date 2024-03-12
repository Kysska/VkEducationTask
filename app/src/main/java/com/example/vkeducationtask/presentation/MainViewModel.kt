package com.example.vkeducationtask.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.vkeducationtask.domain.entity.Product
import com.example.vkeducationtask.domain.usecase.GetCategories
import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getPagingProducts : GetPagingProducts,
    private val getCategories: GetCategories
) : ViewModel() {

    private val _products = MutableLiveData<PagingData<Product>>()
    val products: LiveData<PagingData<Product>>
        get() = _products

    private val _categoriesLiveData = MutableLiveData<List<String>>()
    val categoriesLiveData : LiveData<List<String>>
        get() = _categoriesLiveData


    init {
        loadCategories()
        loadProductsCategory(null)
    }

    fun loadCategories() {
        viewModelScope.launch {
            val categories = withContext(Dispatchers.IO) {
                getCategories.getCategories()
            }
            _categoriesLiveData.value = categories
        }
    }

    fun loadProductsCategory(category: String?){
        viewModelScope.launch {
            getPagingProducts.getPagingProducts(category).cachedIn(viewModelScope).collect{
                _products.value = it
            }
        }
    }

}