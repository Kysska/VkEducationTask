package com.example.vkeducationtask.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.SearchView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.vkeducationtask.R
import com.example.vkeducationtask.app.App
import com.example.vkeducationtask.databinding.FragmentMainBinding
import com.example.vkeducationtask.domain.entity.Product
import com.example.vkeducationtask.presentation.adapter.LoaderAdapter
import com.example.vkeducationtask.presentation.adapter.ProductAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProductsListFragment : Fragment() {
    @Inject
    lateinit var vmFactory: MainViewModelFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: FragmentMainBinding
    private val adapter = ProductAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().applicationContext as App).appComponent.inject(this)
        viewModel = ViewModelProvider(requireActivity(), vmFactory)
            .get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupRetryButton()
        setupOnClickListener()

        setupLoadStateListener()
        observeProducts()
        observeCategories()
    }


    private fun setupRecyclerView() {
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.rv.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(adapter::retry),
            footer = LoaderAdapter(adapter::retry)
        )
    }

    private fun productsByCategory(category: String?){
        viewModel.loadProductsCategory(category)
    }

    private fun setupSearchView(){
        binding.toolbar.findViewById<SearchView>(R.id.search_bar).setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                viewModel.searchProducts()
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                viewModel.setQuery(p0)
                viewModel.filterLocalProducts()
                binding.emptyData.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.GONE
                return true
            }

        })
    }

    private fun setupToolBar(categories: List<String>){
        binding.toolbar.findViewById<ImageButton>(R.id.dropdown_menu).setOnClickListener {
            val popup = PopupMenu(requireContext(), it)

            popup.menu.clear()

            popup.menu.add("all")
            categories.forEach { itemTitle ->
                popup.menu.add(itemTitle)
            }

            popup.setOnMenuItemClickListener {
                when(it.title){
                    "all" -> {
                        productsByCategory(null)
                    }
                    else -> productsByCategory(it.title.toString())
                }
                true
            }

            popup.show()
        }
    }

    private fun setupRetryButton() {
        binding.button2.setOnClickListener {
            adapter.retry()
        }
    }

    private fun setupOnClickListener() {
        adapter.onProductListClickListener = object : ProductAdapter.OnProductListClickListener {
            override fun onProductClickListener(product: Product) {
                (activity as MainActivity?)!!.showProduct(product)
            }

        }
    }

    private fun setupLoadStateListener() {
        adapter.addLoadStateListener { load ->
            binding.apply {
                when {
                    load.refresh is LoadState.Loading -> {
                        rv.visibility = View.GONE
                        emptyData.visibility = View.GONE
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
                        button2.visibility = View.GONE
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
            viewModel.products.observe(viewLifecycleOwner){
                adapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }

    private fun observeCategories() {
        viewModel.categoriesLiveData.observe(viewLifecycleOwner) {
            setupToolBar(it)
            setupSearchView()
        }
    }

    companion object {
        const val TAG = "MainFragment"

    }
}
