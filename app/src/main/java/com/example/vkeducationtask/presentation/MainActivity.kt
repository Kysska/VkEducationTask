package com.example.vkeducationtask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkeducationtask.databinding.ActivityMainBinding
import com.example.vkeducationtask.di.DaggerAppComponent
import com.example.vkeducationtask.domain.entity.Product
import com.example.vkeducationtask.presentation.adapter.LoaderAdapter
import com.example.vkeducationtask.presentation.adapter.ProductAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var vmFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.builder()
            .build()
            .inject(this)

        viewModel = ViewModelProvider(this, vmFactory).get(MainViewModel::class.java)

        setupRecyclerView()
        setupRetryButton()
        setupOnClickListener()

        setupLoadStateListener()
        observeProducts()


    }

    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(this)
        binding.rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(adapter::retry),
            footer = LoaderAdapter(adapter::retry)
        )
    }

    private fun setupRetryButton() {
        binding.button2.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupOnClickListener() {
        adapter.onProductListClickListener = object : ProductAdapter.OnProductListClickListener {
            override fun onProductClickListener(product: Product) {
                val allImages : ArrayList<String> = product.images
                allImages.add(product.thumbnail)
                val fragment = ProductItemFragment.newInstance(
                    title = product.title,
                    price = product.price,
                    desc = product.description,
                    brand = product.brand,
                    discount = product.discountPercentage,
                    rating = product.rating,
                    list_image = allImages
                )
                supportFragmentManager.beginTransaction().replace(binding.itemFragment.id, fragment).commit()
            }

        }
    }

    private fun setupLoadStateListener() {
        adapter.addLoadStateListener { load ->
            binding.apply {
                when {
                    load.refresh is LoadState.Loading -> {
                        rv.visibility = View.GONE
                        progressBar.isVisible = true
                    }

                    load.refresh is LoadState.Error -> {

                        rv.visibility = View.GONE
                        progressBar.visibility = View.GONE
                        emptyData.visibility = View.VISIBLE
                        emptyData.text = "Network error"
                        button2.visibility = View.VISIBLE

                    }

                    else -> {
                        rv.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        if (adapter.itemCount == 0) {
                            emptyData.visibility = View.VISIBLE
                            emptyData.text = "Empty Data"
                        } else {
                            emptyData.visibility = View.GONE
                        }
                    }
                }
            }

        }

    }

    private fun observeProducts() {
        lifecycleScope.launch {
            viewModel.products.collectLatest {
                adapter.submitData(it)
            }
        }
    }


}