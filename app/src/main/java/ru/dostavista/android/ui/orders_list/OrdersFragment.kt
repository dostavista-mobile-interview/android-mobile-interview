package ru.dostavista.android.ui.orders_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import ru.dostavista.android.R
import ru.dostavista.android.data.order.OrderDataProvider
import ru.dostavista.android.databinding.OrdersFragmentBinding
import ru.dostavista.android.di.Injector

class OrdersFragment : Fragment() {

    companion object {
        private val TAG = OrdersFragment::class.java.simpleName

        fun newInstance(): OrdersFragment {
            return OrdersFragment()
        }
    }

    lateinit var orderDataProvider: OrderDataProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Injector.inject(this)
    }

    private var _binding: OrdersFragmentBinding? = null
    private val binding: OrdersFragmentBinding
        get() = _binding ?: error("View doesn't exist")

    private val ordersAdapter = OrdersAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = OrdersFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.title = getString(R.string.orders_screen_title)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = ordersAdapter
    }

    override fun onResume() {
        super.onResume()
        orderDataProvider.loadOrders(
            onSuccess = { orders ->
                ordersAdapter.orders = orders
            },
            onError = { error ->
                Log.e(TAG, "orders loading error", error)
                _binding?.root?.let { parentView ->
                    Snackbar
                        .make(parentView, getString(R.string.orders_loading_error), Snackbar.LENGTH_LONG)
                        .show()
                }
            }
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
