package com.store.shoppingapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.store.shoppingapp.adapter.holder.ProductViewHolder
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.ProductItemBinding
import com.store.shoppingapp.interfaceListener.ClickListener
import com.store.shoppingapp.model.CartItem
import com.store.shoppingapp.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.StringBuilder

class ProductListAdapter (
    private val productList: List<Product>,private val clickListener: ClickListener
) : RecyclerView.Adapter<ProductViewHolder>() {
    private lateinit var binding: ProductItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        binding = ProductItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val productItem = productList[position]
        holder.bind(productItem)
        holder.setIsRecyclable(false);
        GlobalScope.launch {
            val getCartItem = AppDatabase.getDatabase(binding.image1.context).cartDAO()
                .getCartItemQty(productItem.id)
            getCartItem?.let { holder.bind(getCartItem); binding.textnumber.setText(getCartItem.qty.toString()) }
        }
        binding.txtAddQty.setOnClickListener {
            val adapterPosition: Int = holder.getAdapterPosition()
            holder.setIsRecyclable(true);
            GlobalScope.launch {
                val getCartItem = AppDatabase.getDatabase(binding.image1.context).cartDAO().getCartItemQty(productItem.id)
                val qty = getCartItem ?. let {  getCartItem.qty + 1; } ?: 1;
                val re = Regex("[^0-9]")
                val price = re.replace(productItem.price, "")
                val totalPrice = price.toDouble() * qty
                val cartItem = CartItem(
                    productItem.id,
                    productItem.name,
                    productItem.product_id,
                    productItem.sku,
                    productItem.image,
                    productItem.thumb,
                    productItem.zoom_thumb,
                    productItem.description,
                    productItem.price,
                    qty,
                    totalPrice);
                getCartItem ?. let { AppDatabase.getDatabase(binding.image1.context).cartDAO().updateCartItem(cartItem) } ?: AppDatabase.getDatabase(binding.image1.context).cartDAO().insertCartItem(cartItem)
                clickListener.onClick()
            }
            notifyItemChanged(adapterPosition)
        }
        binding.txtRemoveQty.setOnClickListener{
            val adapterPosition: Int = holder.getAdapterPosition()
            holder.setIsRecyclable(true);
            GlobalScope.launch {
                val getCartItem = AppDatabase.getDatabase(binding.image1.context).cartDAO().getCartItemQty(productItem.id)
                getCartItem ?. let {
                    var qty = getCartItem.qty - 1;
                    if (qty <= 0) {
                        qty = 0
                    }
                    val re = Regex("[^0-9]")
                    val price = re.replace(productItem.price, "")
                    val totalPrice = qty * price.toDouble()
                    getCartItem.qty = qty;
                    val cartItem = CartItem(
                        productItem.id,
                        productItem.name,
                        productItem.product_id,
                        productItem.sku,
                        productItem.image,
                        productItem.thumb,
                        productItem.zoom_thumb,
                        productItem.description,
                        productItem.price,
                        qty,
                        totalPrice);
                    AppDatabase.getDatabase(binding.image1.context).cartDAO()
                        .updateCartItem(cartItem )
                    clickListener.onClick()
                }
            }
            notifyItemChanged(adapterPosition)
        }

    }

    override fun getItemCount(): Int = productList.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }
    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}