package ru.dostavista.android;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ru.dostavista.android.data.Order;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrderViewHolder> {

    private List<Order> orders;

    static class OrderViewHolder extends RecyclerView.ViewHolder {
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

    public OrdersAdapter(List<Order> orders) {
        this.orders = orders;
    }

    @NonNull
    @Override
    public OrdersAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OrderViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.order_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);

        holder.addressTextView.setText(order.getAddress());
        holder.idTextView.setText(order.getOrderId());
        holder.commentTextView.setText(order.getText());
        holder.dateTextView.setText(order.getDate());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public OrdersAdapter setOrders(List<Order> orders) {
        this.orders = orders;
        return this;
    }
}