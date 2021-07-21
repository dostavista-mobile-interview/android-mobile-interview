package ru.dostavista.android.data.order.remote

import retrofit2.Call
import retrofit2.http.GET

interface OrderApi {

    @GET("mobile-interview-api.php")
    fun loadOrders(): Call<OrderListResponse>

}
