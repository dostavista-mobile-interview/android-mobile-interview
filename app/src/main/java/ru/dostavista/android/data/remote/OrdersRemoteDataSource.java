package ru.dostavista.android.data.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.dostavista.android.data.Order;
import ru.dostavista.android.data.OrdersDataSource;
import ru.dostavista.android.http.HttpClient;
import ru.dostavista.android.http.Request;
import ru.dostavista.android.http.Response;

public class OrdersRemoteDataSource implements OrdersDataSource {

    private HttpClient httpClient;

    public OrdersRemoteDataSource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void getOrders(final Integer sinceId, final Integer limit, final LoadOrdersCallback loadOrdersCallback) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                String endpoint = "mobile-interview-api.php";
                if (sinceId != null) endpoint += "?since_id=" + sinceId;
                if (limit != null) endpoint += "&limit=" + limit;
                Request ordersRequest = new Request(endpoint);
                Response response = httpClient.execute(ordersRequest);
                String tag = "network";
                Log.d(tag, "Request: " + ordersRequest.getPath());
                Log.d(tag, "Response: " + response.getJson());
                if (response.isSuccess()) {
                    List<Order> newOrders = new ArrayList<>();
                    JSONObject jsonObject = response.getJson();
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("orders");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            newOrders.add(Order.fromJson(jsonArray.getJSONObject(i)));
                        }
                        loadOrdersCallback.onOrdersLoaded(newOrders);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        loadOrdersCallback.onOrdersNotAvailable();
                    }
                } else {
                    loadOrdersCallback.onOrdersNotAvailable();
                }
            }
        });
    }
}
