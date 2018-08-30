package ru.dostavista.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import ru.dostavista.android.util.ActivityUtils;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orders_activity);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        OrdersFragment ordersFragment = (OrdersFragment) getSupportFragmentManager().findFragmentById(R.id.contentFrame);
        if (ordersFragment == null) {
            ordersFragment = OrdersFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    ordersFragment, R.id.contentFrame);
        }
    }
}
