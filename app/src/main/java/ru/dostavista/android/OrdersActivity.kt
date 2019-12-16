package ru.dostavista.android

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import kotlinx.android.synthetic.main.orders_activity.*

import ru.dostavista.android.util.ActivityUtils

class OrdersActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_activity)
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            ActivityUtils.addFragmentToActivity(supportFragmentManager,
                    OrdersFragment.newInstance(), R.id.contentFrame)
        }
    }
}
