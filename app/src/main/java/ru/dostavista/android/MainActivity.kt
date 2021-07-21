package ru.dostavista.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.dostavista.android.ui.orders_list.OrdersFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                add(R.id.rootContainer, OrdersFragment.newInstance())
            }.commit()
        }
    }
}
