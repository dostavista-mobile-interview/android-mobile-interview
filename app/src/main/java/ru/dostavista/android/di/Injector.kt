package ru.dostavista.android.di

import android.os.Handler
import android.os.Looper
import ru.dostavista.android.data.api.ApiBuilderImpl
import ru.dostavista.android.data.api.ApiBuilder
import ru.dostavista.android.data.order.OrderDataProvider
import ru.dostavista.android.data.order.OrderDataProviderImpl
import ru.dostavista.android.data.order.remote.OrderApi
import ru.dostavista.android.ui.orders_list.OrdersFragment
import java.util.concurrent.Executor
import java.util.concurrent.Executors

object Injector {

    private val backgroundExecutor = Executors.newCachedThreadPool()

    private val uiHandler = Handler(Looper.getMainLooper())
    private val uiThreadExecutor = Executor { uiHandler.post(it) }

    private val apiBuilder: ApiBuilder = ApiBuilderImpl()

    private val orderDataProvider: OrderDataProvider = OrderDataProviderImpl(
        api = apiBuilder.createApi(OrderApi::class.java),
        uiThreadExecutor = uiThreadExecutor,
        backgroundExecutor = backgroundExecutor
    )

    fun inject(ordersFragment: OrdersFragment) {
        ordersFragment.orderDataProvider = orderDataProvider
    }

}
