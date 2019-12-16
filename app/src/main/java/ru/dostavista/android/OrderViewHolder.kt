package ru.dostavista.android

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.order_view_holder.*
import ru.dostavista.android.data.Order

class OrderViewHolder(
        override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bind(order: Order) {
        idTextView.text = order.orderId
        addressTextView.text = order.address
        commentTextView.text = order.text
        dateTextView.text = order.date
    }

}