package com.store.shoppingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.shoppingapp.R
import com.store.shoppingapp.adapter.ProductListAdapter
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.ActivityProductListBinding
import com.store.shoppingapp.interfaceListener.ClickListener
import com.store.shoppingapp.model.CartItem
import com.store.shoppingapp.model.ProductList
import kotlinx.coroutines.*

class ProductListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProductListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_product_list)
        val recyclerViewProduct = binding.rvProductList
        val textView: TextView = findViewById(R.id.myImageViewText) as TextView
        GlobalScope.launch {
            val productList = AppDatabase.getDatabase(this@ProductListActivity).productDAO().gelAllProducts()
            val cartCount = AppDatabase.getDatabase(this@ProductListActivity).cartDAO().getCartItemCount()
            if (cartCount > 0) {
                textView.visibility =View.VISIBLE
                textView.text = cartCount.toString()
            }else{
                textView.visibility =View.GONE
            }
            val productListAdapter = ProductListAdapter(productList, object : ClickListener {
                override fun onClick() {
                    GlobalScope.launch {
                        val getCartCount =
                            AppDatabase.getDatabase(this@ProductListActivity).cartDAO()
                                .getCartItemCount()
                        withContext(Dispatchers.Main){
                            if (getCartCount > 0) {
                                textView.visibility =View.VISIBLE
                                textView.text = getCartCount.toString()
                            }else{
                                textView.visibility = View.GONE
                            }
                        }
                    }
                }
            })
            recyclerViewProduct.adapter = productListAdapter
        }
        val layoutManager = GridLayoutManager(this, 2)
        recyclerViewProduct.layoutManager = layoutManager
        recyclerViewProduct.setHasFixedSize(true)
        recyclerViewProduct.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )
        binding.conCart.setOnClickListener {
            onClcikCartButton()
        }
    }

    fun onClcikCartButton(){
        val intent = Intent(this@ProductListActivity,CartActivity :: class.java)
        startActivity(intent)
    }
}