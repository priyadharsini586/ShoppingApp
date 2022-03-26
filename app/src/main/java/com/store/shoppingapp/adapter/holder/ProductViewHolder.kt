package com.store.shoppingapp.adapter.holder

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.store.shoppingapp.adapter.ProductListAdapter
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.ProductItemBinding
import com.store.shoppingapp.model.CartItem
import com.store.shoppingapp.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductViewHolder( private val binding: ProductItemBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(product: Product) {
        binding.productItem = product
    }
    fun bind(cartItem: CartItem) {
        GlobalScope.launch {
            val getCartCount = AppDatabase.getDatabase(binding.image1.context).cartDAO().getCartItemCount()
            getCartCount ?. let { withContext(Dispatchers.IO){ binding.setVariable(BR.cartItem, cartItem);} }
        }
    }
}