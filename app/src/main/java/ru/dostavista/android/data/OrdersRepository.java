package ru.dostavista.android.data;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import ru.dostavista.android.data.remote.OrdersRemoteDataSource;

public class OrdersRepository implements OrdersDataSource {

    private static OrdersRepository INSTANCE = null;

    private OrdersRemoteDataSource ordersRemoteDataSource;

    Handler handler = new Handler(Looper.getMainLooper());

    private List<Order> cachedOrders;

    public static OrdersRepository getInstance(OrdersRemoteDataSource ordersRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRepository(ordersRemoteDataSource);
        }
        return INSTANCE;
    }

    public OrdersRepository(OrdersRemoteDataSource ordersRemoteDataSource) {
        this.ordersRemoteDataSource = ordersRemoteDataSource;
    }

    @Override
    public void getOrders(final LoadOrdersCallback loadOrdersCallback) {
        ordersRemoteDataSource.getOrders(new LoadOrdersCallback() {
            @Override
            public void onOrdersLoaded(final List<Order> orders) {
                cachedOrders = orders;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadOrdersCallback.onOrdersLoaded(orders);
                    }
                });
            }

            @Override
            public void onOrdersNotAvailable() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadOrdersCallback.onOrdersNotAvailable();
                    }
                });
            }
        });
    }
}
