package ru.dostavista.android.data.order.local

import ru.dostavista.android.data.order.remote.OrderDto

data class Order(
    val orderId: String,
    val address: String,
    val date: String,
    val text: String
) {

    constructor(dto: OrderDto) : this(
        orderId = dto.orderId!!,
        address = dto.address!!,
        date = dto.date!!,
        text = dto.text!!
    )

}
