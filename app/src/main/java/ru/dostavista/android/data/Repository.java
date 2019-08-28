package ru.dostavista.android.data;

import java.util.List;

public interface Repository {

    interface LoadOrdersCallback {

        void onOrdersLoaded(List<Order> orders, boolean isNextPage);

        void onOrdersNotAvailable();
    }

    void getOrders(Integer sinceId, LoadOrdersCallback loadOrdersCallback);

}
