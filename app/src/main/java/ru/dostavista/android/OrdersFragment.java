package ru.dostavista.android;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import data.Injection;
import ru.dostavista.android.data.Order;
import ru.dostavista.android.data.OrdersDataSource;
import ru.dostavista.android.data.OrdersRepository;

public class OrdersFragment extends Fragment {

    private RecyclerView recyclerView;
    private OrdersRepository ordersRepository;
    private static String TAG = OrdersFragment.class.getSimpleName();
    private OrdersAdapter ordersAdapter;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ordersRepository = Injection.provideOrdersRepository();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.orders_fragment, container, false);
        recyclerView = root.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        ordersRepository.getOrders(new OrdersDataSource.LoadOrdersCallback() {
            @Override
            public void onOrdersLoaded(List<Order> orders) {
                Log.d(TAG, "orders loaded size: " + String.valueOf(orders.size()));
                update(orders);
            }

            @Override
            public void onOrdersNotAvailable() {
                Log.d(TAG, "orders load error");
            }
        });
    }

    private void update(List<Order> orders) {
        if (ordersAdapter == null) {
            ordersAdapter = new OrdersAdapter(orders);
            recyclerView.setAdapter(ordersAdapter);
        } else {
            ordersAdapter.setOrders(orders);
        }
        ordersAdapter.notifyDataSetChanged();
    }
}
