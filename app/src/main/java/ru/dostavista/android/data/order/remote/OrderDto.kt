package ru.dostavista.android.data.order.remote

import com.google.gson.annotations.SerializedName

data class OrderDto(
    @SerializedName("order_id") val orderId: String?,
    @SerializedName("address") val address: String?,
    @SerializedName("date") val date: String?,
    @SerializedName("text") val text: String?
)
