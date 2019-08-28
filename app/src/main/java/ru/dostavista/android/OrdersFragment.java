package ru.dostavista.android;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import data.Injection;
import ru.dostavista.android.data.Order;
import ru.dostavista.android.data.Repository;

public class OrdersFragment extends Fragment {

    private static String TAG = OrdersFragment.class.getSimpleName();

    private Repository<Order> ordersRepository;

    SwipeRefreshLayout srl;

    RecyclerView.LayoutManager lm;

    private RecyclerView recyclerView;

    private OrdersAdapter ordersAdapter;

    private String lastId = null;

    public static OrdersFragment newInstance() {
        return new OrdersFragment();
    }

    private Repository.RepositoryCallback callback = new Repository.RepositoryCallback<Order>() {

        @Override
        public void onOrdersLoaded(List<Order> orders, boolean isNextPage) {
            Log.d(TAG, "orders loaded size: " + orders.size());
            update(orders, isNextPage);
            srl.setRefreshing(false);
        }

        @Override
        public void onOrdersNotAvailable() {
            Log.d(TAG, "orders load error");
            srl.setRefreshing(false);
        }

    };

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
        lm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(lm);
        ordersAdapter = new OrdersAdapter();
        recyclerView.setAdapter(ordersAdapter);
        srl = root.findViewById(R.id.swipeToRefresh);
        srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });
        setupUpOnScroll();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fetchData();
    }

    private void setupUpOnScroll() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1)) {
                    loadNextPage();
                }
            }
        });
    }

    private void loadNextPage() {
        List<Order> orders = ordersAdapter.getOrders();
        Order lastOrder = !orders.isEmpty() ? orders.get(orders.size() - 1) : null;
        String nextPageId = lastOrder != null ? lastOrder.getOrderId() : null;
        boolean hasNextPage = nextPageId != null && !nextPageId.equals(lastId);
        if (hasNextPage) {
            ordersAdapter.showLoading(true);
            lastId = nextPageId;
            ordersRepository.getData(Integer.parseInt(nextPageId), callback);
        } else {
            ordersAdapter.showLoading(false);
        }
    }

    private void fetchData() {
        srl.setRefreshing(true);
        ordersRepository.getData(null, callback);

    }

    private void update(List<Order> orders, boolean isNextPage) {
        if (isNextPage) ordersAdapter.addOrders(orders);
        else ordersAdapter.setOrders(orders);
    }

}
