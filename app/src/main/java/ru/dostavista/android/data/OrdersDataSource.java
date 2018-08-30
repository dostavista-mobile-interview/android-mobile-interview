package ru.dostavista.android.data;

import java.util.List;

public interface OrdersDataSource {

    interface LoadOrdersCallback {

        void onOrdersLoaded(List<Order> orders);

        void onOrdersNotAvailable();
    }

    void getOrders(LoadOrdersCallback loadOrdersCallback);
}
