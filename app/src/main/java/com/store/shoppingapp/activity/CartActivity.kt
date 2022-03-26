package com.store.shoppingapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.store.shoppingapp.R
import com.store.shoppingapp.adapter.CartListAdapter
import com.store.shoppingapp.adapter.ProductListAdapter
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.databinding.ActivityCartBinding
import com.store.shoppingapp.databinding.ActivityProductListBinding
import com.store.shoppingapp.interfaceListener.ClickListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CartActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCartBinding
    var totalItemCartPrice : Double = 0.0;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cart)
        val recyclerViewProduct = binding.rvCartList
        getTotalPriceValue()
        GlobalScope.launch {
            val cartItemList = AppDatabase.getDatabase(this@CartActivity).cartDAO().gelAllCartItem()
            val productListAdapter = CartListAdapter(cartItemList,object : ClickListener{
                override fun onClick() {
                    getTotalPriceValue()
                }
            })
            recyclerViewProduct.adapter = productListAdapter
        }
        val layoutManager = LinearLayoutManager(this)
        recyclerViewProduct.layoutManager = layoutManager
        recyclerViewProduct.setHasFixedSize(true)
        recyclerViewProduct.addItemDecoration(
            DividerItemDecoration(
                this,
                layoutManager.orientation
            )
        )
        binding.layHome.setOnClickListener {
            onClcikCartButton()
        }
    }
    fun onClcikCartButton(){
        val intent = Intent(this@CartActivity,ProductListActivity :: class.java)
        startActivity(intent)
    }
    fun getTotalPriceValue(){
        GlobalScope.launch {
            val cartItemList = AppDatabase.getDatabase(this@CartActivity).cartDAO().gelAllCartItem()
            totalItemCartPrice = 0.0
            for (cart in cartItemList) {
                totalItemCartPrice += cart.totalPrice
            }
            withContext(Dispatchers.Main){
                binding.txtTotalPrice.text = "₹ "+ totalItemCartPrice.toString()
            }
        }
    }
}