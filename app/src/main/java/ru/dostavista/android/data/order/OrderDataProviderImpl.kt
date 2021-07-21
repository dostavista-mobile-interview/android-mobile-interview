package ru.dostavista.android.data.order

import ru.dostavista.android.data.order.local.Order
import ru.dostavista.android.data.order.remote.OrderApi
import java.util.concurrent.Executor

class OrderDataProviderImpl(
    private val api: OrderApi,
    private val backgroundExecutor: Executor,
    private val uiThreadExecutor: Executor
) : OrderDataProvider {

    override fun loadOrders(onSuccess: (List<Order>) -> Unit, onError: (Throwable) -> Unit) {
        backgroundExecutor.execute {
            try {
                val response = api.loadOrders().execute()
                if (!response.isSuccessful) {
                    throw Exception("Orders request failed with code ${response.code()}")
                }

                val orders = response
                    .body()!!
                    .orders!!
                    .map { Order(it) }

                uiThreadExecutor.execute {
                    onSuccess(orders)
                }
            } catch (e: Throwable) {
                uiThreadExecutor.execute {
                    onError(e)
                }
            }
        }
    }

}
