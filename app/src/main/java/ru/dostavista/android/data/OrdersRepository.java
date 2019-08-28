package ru.dostavista.android.data;

import android.os.Handler;
import android.os.Looper;

import java.util.List;

import ru.dostavista.android.data.remote.OrdersRemoteDataSource;

public class OrdersRepository implements Repository {

    private static OrdersRepository INSTANCE = null;
    Handler handler = new Handler(Looper.getMainLooper());
    private OrdersRemoteDataSource ordersRemoteDataSource;
    private List<Order> cachedOrders;



    public OrdersRepository(OrdersRemoteDataSource ordersRemoteDataSource) {
        this.ordersRemoteDataSource = ordersRemoteDataSource;
    }

    public static OrdersRepository getInstance(OrdersRemoteDataSource ordersRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRepository(ordersRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getOrders(final Integer sinceId, final LoadOrdersCallback loadOrdersCallback) {
        ordersRemoteDataSource.getOrders(sinceId, null, new OrdersDataSource.LoadOrdersCallback() {
            @Override
            public void onOrdersLoaded(final List<Order> orders) {
                cachedOrders = orders;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        loadOrdersCallback.onOrdersLoaded(orders,sinceId != null);
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
