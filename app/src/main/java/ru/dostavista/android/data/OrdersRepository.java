package ru.dostavista.android.data;

import android.os.Handler;
import android.os.Looper;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import ru.dostavista.android.data.remote.OrdersRemoteDataSource;

public class OrdersRepository implements Repository<Order> {

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
    public void getData(final Integer sinceId, final RepositoryCallback<Order> repositoryCallback) {
        final boolean isNextPage = sinceId != null;
        ordersRemoteDataSource.getOrders(sinceId, null, new OrdersDataSource.LoadOrdersCallback() {
            @Override
            public void onOrdersLoaded(final List<Order> orders) {
                cachedOrders.addAll(orders);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        repositoryCallback.onOrdersLoaded(orders, isNextPage);
                    }
                });
            }

            @Override
            public void onOrdersNotAvailable() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (cachedOrders != null && sinceId == null) {
                            repositoryCallback.onOrdersLoaded(new ArrayList<>(cachedOrders), false);
                        } else {
                            repositoryCallback.onOrdersNotAvailable();
                        }
                    }
                });
            }
        });
    }

}
