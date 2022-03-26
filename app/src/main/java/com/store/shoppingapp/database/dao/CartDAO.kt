package com.store.shoppingapp.database.dao

import androidx.room.*
import com.store.shoppingapp.model.CartItem

@Dao
interface CartDAO {
    @Insert
    fun insertCartItem(cartItem: CartItem)

    @Query("Select * from cart_item")
    fun gelAllCartItem(): List<CartItem>

    @Update(onConflict = OnConflictStrategy.IGNORE)
    fun updateCartItem(cartItem: CartItem)

    @Query("UPDATE cart_item SET qty = :qty and total_price = :total_price  WHERE id = :id")
    fun updateCartItemQty(id: String,qty : Int,total_price: Double)

    @Query("Select * From cart_item WHERE id == :id ")
    fun getCartItemQty(id: String): CartItem

    @Query("Select count(*) From cart_item")
    fun getCartItemCount(): Int
}