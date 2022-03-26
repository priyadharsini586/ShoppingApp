package com.store.shoppingapp.network

import com.store.shoppingapp.model.ProductList
import retrofit2.Response
import retrofit2.http.GET

interface ProductsApi {
    @GET("/v2/5def7b172f000063008e0aa2")
    suspend fun getProductsList() : Response<ProductList>
}