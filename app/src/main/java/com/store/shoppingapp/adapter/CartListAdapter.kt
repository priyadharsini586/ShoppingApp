package com.store.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.shoppingapp.adapter.holder.CartItemHolder
import com.store.shoppingapp.adapter.holder.ProductViewHolder
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.CartItemBinding
import com.store.shoppingapp.databinding.ProductItemBinding
import com.store.shoppingapp.interfaceListener.ClickListener
import com.store.shoppingapp.model.CartItem
import com.store.shoppingapp.model.Product
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartListAdapter( private val cartList: List<CartItem> ,private val clickListener: ClickListener
) : RecyclerView.Adapter<CartItemHolder>() {
    private lateinit var binding: CartItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemHolder {
        binding = CartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false);
        return CartItemHolder(binding)
    }

    override fun onBindViewHolder(holder: CartItemHolder, position: Int) {
        val cartItem = cartList[position]
        holder.bind(cartItem)
        binding.txtAddQty.setOnClickListener {
            val adapterPosition: Int = holder.getAdapterPosition()
            holder.setIsRecyclable(true);
            val qty = cartItem ?. let {  cartItem.qty + 1; } ?: 1;
            val re = Regex("[^0-9]")
            val price = re.replace(cartItem.price, "")
            val totalPrice = price.toDouble() * qty
            cartItem.qty = qty;
            cartItem.totalPrice = totalPrice
            GlobalScope.launch {
                cartItem ?. let { AppDatabase.getDatabase(binding.layContent.context).cartDAO().updateCartItem(cartItem) } ?: AppDatabase.getDatabase(binding.image2.context).cartDAO().insertCartItem(cartItem)
                clickListener.onClick()
            }
            notifyItemChanged(adapterPosition)
        }
        binding.txtRemoveQty.setOnClickListener{
            val adapterPosition: Int = holder.getAdapterPosition()
            holder.setIsRecyclable(true);
            var qty = cartItem.qty - 1;
            if (qty <= 0) {
                qty = 0
            }
            val re = Regex("[^0-9]")
            val price = re.replace(cartItem.price, "")
            val totalPrice = qty * price.toDouble()
            cartItem.qty = qty;
            cartItem.totalPrice = totalPrice
            GlobalScope.launch {
                AppDatabase.getDatabase(binding.image2.context).cartDAO()
                        .updateCartItem(cartItem)
                clickListener.onClick()
            }
            notifyItemChanged(adapterPosition)
        }
    }

    override fun getItemCount(): Int = cartList.size
}