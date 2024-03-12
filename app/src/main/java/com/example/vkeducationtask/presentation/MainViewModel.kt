package com.example.vkeducationtask.presentation



import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.filter
import com.example.vkeducationtask.domain.entity.Product
import com.example.vkeducationtask.domain.usecase.GetCategories
import com.example.vkeducationtask.domain.usecase.GetPagingProducts
import com.example.vkeducationtask.domain.usecase.GetSearchProductsPaging
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(
    private val getPagingProducts : GetPagingProducts,
    private val getCategories: GetCategories,
    private val getSearchProductsPaging: GetSearchProductsPaging
) : ViewModel() {

    private val _products = MutableLiveData<PagingData<Product>>()
    val products: LiveData<PagingData<Product>>
        get() = _products

    private val _originalProducts = MutableLiveData<PagingData<Product>>()
    val originalProducts: LiveData<PagingData<Product>>
        get() = _originalProducts

    private val _categoriesLiveData = MutableLiveData<List<String>>()
    val categoriesLiveData : LiveData<List<String>>
        get() = _categoriesLiveData

    private var currentQuery : String? = null


    init {
        loadCategories()
        loadProductsCategory(null)
    }

    fun setQuery(query : String?){
        currentQuery = query
    }

    fun filterLocalProducts() {
        val query = currentQuery
        viewModelScope.launch {
            val filteredProducts = if (!query.isNullOrBlank()) {
                val filteredList = originalProducts.value?.filter {
                    it.title.contains(query, ignoreCase = true)
                }
                filteredList ?: PagingData.empty()
            } else {
                originalProducts.value ?: PagingData.empty()
            }
            _products.value = filteredProducts
        }
    }

    fun searchProducts(){
        val query = currentQuery
        viewModelScope.launch {
            getSearchProductsPaging.getSearchPagingProducts(query = query).cachedIn(viewModelScope).collect{
                _products.value = it
            }

        }
    }

    private fun loadCategories() {
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
                _originalProducts.value = it
            }
        }
    }

}