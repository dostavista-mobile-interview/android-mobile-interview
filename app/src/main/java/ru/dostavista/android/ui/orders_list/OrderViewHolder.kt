package ru.dostavista.android.ui.orders_list

import androidx.recyclerview.widget.RecyclerView
import ru.dostavista.android.data.order.local.Order
import ru.dostavista.android.databinding.OrderViewHolderBinding

class OrderViewHolder(
        val binding: OrderViewHolderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(order: Order) {
        binding.idTextView.text = order.orderId
        binding.addressTextView.text = order.address
        binding.commentTextView.text = order.text
        binding.dateTextView.text = order.date
    }

}
