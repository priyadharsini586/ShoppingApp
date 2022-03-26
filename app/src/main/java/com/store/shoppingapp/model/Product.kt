package com.store.shoppingapp.model

import android.net.Uri
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide

@Entity(tableName = "Products")
data class Product(
    @PrimaryKey
    val id   : String,
    @ColumnInfo(name = "name")
    val name : String,
    @ColumnInfo(name = "product_id")
    val product_id : String,
    @ColumnInfo(name = "sku")
    val sku : String,
    @ColumnInfo(name = "image")
    val image : String,
    @ColumnInfo(name = "thumb")
    val thumb : String,
    @ColumnInfo(name = "zoom_thumb")
    val zoom_thumb : String,
    @ColumnInfo(name = "description")
    val description : String,
    @ColumnInfo(name = "quantity")
    val quantity : String,
    @ColumnInfo(name = "price")
    val price : String,
    @ColumnInfo(name = "special")
    val special : String,
)
@BindingAdapter("urlImage")
fun loadImageWithUri(imageView: ImageView, imageUri: String) {
    Glide.with(imageView.context).load(Uri.parse(imageUri)).into(imageView)
}
