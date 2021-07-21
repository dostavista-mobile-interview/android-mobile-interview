package ru.dostavista.android.data.order

import ru.dostavista.android.data.order.local.Order

interface OrderDataProvider {

    fun loadOrders(onSuccess: (List<Order>) -> Unit, onError: (Throwable) -> Unit)

}
