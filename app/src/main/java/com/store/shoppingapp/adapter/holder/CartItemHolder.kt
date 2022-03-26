package com.store.shoppingapp.adapter.holder

import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.CartItemBinding
import com.store.shoppingapp.model.CartItem


class CartItemHolder ( private val binding: CartItemBinding)
    : RecyclerView.ViewHolder(binding.root) {
    fun bind(cartItem: CartItem) {
        binding.cartItem = cartItem
    }
}