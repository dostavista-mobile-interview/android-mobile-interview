package ru.dostavista.android

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import data.Injection
import kotlinx.android.synthetic.main.orders_fragment.*
import ru.dostavista.android.data.Order
import ru.dostavista.android.data.OrdersDataSource
import ru.dostavista.android.data.OrdersRepository

class OrdersFragment : Fragment() {

    companion object {
        private val TAG = OrdersFragment::class.java.simpleName

        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    private lateinit var ordersRepository: OrdersRepository
    private var ordersAdapter: OrdersAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ordersRepository = Injection.provideOrdersRepository()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.orders_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(context)
        ordersAdapter = OrdersAdapter()
        recyclerView.adapter = ordersAdapter
    }

    override fun onResume() {
        super.onResume()
        ordersRepository.getOrders(object : OrdersDataSource.LoadOrdersCallback {
            override fun onOrdersLoaded(orders: List<Order>) {
                Log.d(TAG, "orders loaded size: " + orders.size.toString())
                update(orders)
            }

            override fun onOrdersNotAvailable() {
                Log.d(TAG, "orders load error")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        ordersAdapter = null
    }

    private fun update(orders: List<Order>) {
        ordersAdapter?.orders = orders
    }

}
