package com.example.vkeducationtask.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vkeducationtask.databinding.ActivityMainBinding
import com.example.vkeducationtask.di.DaggerAppComponent
import com.example.vkeducationtask.domain.entity.Product

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DaggerAppComponent.builder()
            .build()
            .inject(this)

        if(savedInstanceState == null){
            val fragment = ProductsListFragment()
            supportFragmentManager.beginTransaction().add(binding.mainContainer.id, fragment, ProductsListFragment.TAG).commit()
        }


    }

    fun showProduct(product: Product){
        val fragment = ProductItemFragment.newInstance(
            product = product
        )
        supportFragmentManager.beginTransaction().addToBackStack(ProductItemFragment.TAG).replace(binding.mainContainer.id, fragment, null).commit()
    }


}