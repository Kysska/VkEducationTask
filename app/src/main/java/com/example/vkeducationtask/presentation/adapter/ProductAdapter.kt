package com.example.vkeducationtask.presentation.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vkeducationtask.R
import com.example.vkeducationtask.databinding.CardProductBinding
import com.example.vkeducationtask.domain.entity.Product
import com.squareup.picasso.Picasso

class ProductAdapter : PagingDataAdapter<Product, RecyclerView.ViewHolder>(COMPARATOR) {

    var onProductListClickListener: OnProductListClickListener? = null
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
         val productItem = getItem(position)
        holder.itemView.setOnClickListener {
             if (productItem != null) {
                 onProductListClickListener?.onProductClickListener(productItem)
             }
         }
        if(productItem != null){
            (holder as ProductViewHolder).bind(productItem)
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.card_product, parent, false)
        return ProductViewHolder(view)
    }

    class ProductViewHolder(view : View) : RecyclerView.ViewHolder(view){

        val binding = CardProductBinding.bind(view)

        fun bind(product: Product){
            Picasso.get().load(product.thumbnail).into(binding.imageView)
            binding.title.text = product.title
            binding.description.text = product.description
        }

    }

    companion object{
        private val COMPARATOR = object : DiffUtil.ItemCallback<Product>(){
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
               return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }

    interface OnProductListClickListener{
        fun onProductClickListener(product: Product)
    }
}