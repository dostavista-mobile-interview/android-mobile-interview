package ru.dostavista.android.data;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ru.dostavista.android.data.remote.OrderParams;
import ru.dostavista.android.data.remote.OrdersRemoteDataSource;

public class OrdersRepository implements Repository<OrderParams, List<Order>> {

    private static OrdersRepository INSTANCE = null;

    private OrdersRemoteDataSource ordersRemoteDataSource;

    private Handler handler = new Handler(Looper.getMainLooper());

    private Set<Order> cachedOrders = new LinkedHashSet<>();

    private OrdersRepository(OrdersRemoteDataSource ordersRemoteDataSource) {
        this.ordersRemoteDataSource = ordersRemoteDataSource;
    }

    public static OrdersRepository getInstance(OrdersRemoteDataSource ordersRemoteDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new OrdersRepository(ordersRemoteDataSource);
        }
        return INSTANCE;
    }

    @Override
    public void getData(final OrderParams params, final RepositoryCallback<List<Order>> repositoryCallback) {
        final boolean isNextPage = params.sinceId != null;
        ordersRemoteDataSource.getData(params, new DataSource.Callback<List<Order>>() {
            @Override
            public void onDataLoaded(final List<Order> orders) {
                cachedOrders.addAll(orders);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        repositoryCallback.onOrdersLoaded(orders, isNextPage);
                    }
                });
            }

            @Override
            public void onError() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cachedOrders != null && params.sinceId == null) {
                            repositoryCallback.onOrdersLoaded(new ArrayList<>(cachedOrders), false);
                        } else {
                            repositoryCallback.onError();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void dispose() {
        ordersRemoteDataSource.dispose();
    }
}
