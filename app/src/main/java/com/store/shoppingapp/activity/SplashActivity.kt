package com.store.shoppingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import android.widget.Toast
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.store.shoppingapp.R
import com.store.shoppingapp.database.AppDatabase
import com.store.shoppingapp.model.Product
import com.store.shoppingapp.network.ProductsApi
import com.store.shoppingapp.network.RetrofitHelper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.content.Intent


class SplashActivity : AppCompatActivity() {
    var progressBar: CircularProgressIndicator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        progressBar = findViewById<CircularProgressIndicator>(R.id.progress_circular)
        progressBar?.visibility = View.VISIBLE;
        val  productApi = RetrofitHelper.getInstance().create(ProductsApi :: class.java)
        GlobalScope.launch {
            val response = productApi.getProductsList()
            if (response.isSuccessful && response != null) {
                var data = response.body()!!
                AppDatabase.getDatabase(this@SplashActivity).productDAO().insertAllProducts(data.products)
                this@SplashActivity.runOnUiThread(Runnable {
                    progressBar?.visibility = View.GONE;
                    val intent = Intent(this@SplashActivity,ProductListActivity :: class.java)
                    startActivity(intent)
                    finish()
                })
            }else {
                Toast.makeText(this@SplashActivity,"Error Occurred: ${response.message()}",Toast.LENGTH_LONG).show()
            }
        }
    }
}