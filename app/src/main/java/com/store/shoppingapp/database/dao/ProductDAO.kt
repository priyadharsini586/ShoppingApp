package com.store.shoppingapp.database.dao

import androidx.room.*
import com.store.shoppingapp.model.Product

@Dao
interface ProductDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllProducts(product: List<Product>)
    @Insert
    fun insertProduct(product: Product)

    @Query("Select * from Products")
    fun gelAllProducts(): List<Product>

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)
}