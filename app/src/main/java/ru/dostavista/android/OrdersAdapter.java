package ru.dostavista.android;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.dostavista.android.data.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.ViewHolder> {

    private enum ItemType {

        Order(0), LoadingIndicator(1);

        private final int key;

        ItemType(int key) {
            this.key = key;
        }

    }

    private List<Order> orders;

    private boolean showLoading = false;

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ItemType.Order.key) {
            return new OrderViewHolder(inflateLayout(R.layout.order_view_holder, parent));
        } else {
            return new LoadingIndicatorViewHolder(inflateLayout(R.layout.loading_view_holder, parent));
        }
    }

    private View inflateLayout(@LayoutRes int layout, ViewGroup parent) {
        return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
    }

    @Override
    public int getItemViewType(int position) {
        boolean isLastItem = position >= orders.size();
        return isLastItem ? ItemType.LoadingIndicator.key : ItemType.Order.key;
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersAdapter.ViewHolder holder, int position) {
        if (getItemViewType(position) == ItemType.Order.key) {
            Order order = orders.get(position);
            OrderViewHolder orderViewHolder = (OrderViewHolder) holder;
            orderViewHolder.addressTextView.setText(order.getAddress());
            orderViewHolder.idTextView.setText(order.getOrderId());
            orderViewHolder.commentTextView.setText(order.getText());
            orderViewHolder.dateTextView.setText(order.getDate());
        }
    }

    public void showLoading(boolean showLoading) {
        this.showLoading = showLoading;
        notifyDataSetChanged();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        notifyDataSetChanged();
    }

    public void addOrders(List<Order> newOrders) {
        int oldSize = this.orders.size();
        this.orders.addAll(newOrders);
        notifyItemRangeInserted(oldSize, newOrders.size());
    }

    @Override
    public int getItemCount() {
        return showLoading ? orders.size() + 1 : orders.size(); // Orders + an additional item to show a spinner
    }

    static abstract class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class LoadingIndicatorViewHolder extends ViewHolder {

        public LoadingIndicatorViewHolder(@NonNull View itemView) {
            super(itemView);
        }

    }

    private class OrderViewHolder extends ViewHolder {

        TextView idTextView;
        TextView addressTextView;
        TextView dateTextView;
        TextView commentTextView;

        OrderViewHolder(View v) {
            super(v);
            idTextView = v.findViewById(R.id.idTextView);
            addressTextView = v.findViewById(R.id.addressTextView);
            dateTextView = v.findViewById(R.id.dateTextView);
            commentTextView = v.findViewById(R.id.commentTextView);
        }

    }

}