package com.example.vkeducationtask.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.vkeducationtask.R
import com.example.vkeducationtask.databinding.LoaderItemBinding

class LoaderAdapter(private val retry: () -> Unit) : LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(view: View, private val retry: () -> Unit) : RecyclerView.ViewHolder(view){
         val binding = LoaderItemBinding.bind(view)

        fun bind(loadState : LoadState){
            binding.progressBar2.isVisible = loadState is LoadState.Loading
            binding.errorView.isVisible = loadState is LoadState.Error
            binding.button.isVisible = loadState is LoadState.Error
            binding.button.setOnClickListener{
                retry()
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.loader_item, parent, false)
        return  LoaderViewHolder(view, retry)
    }
}