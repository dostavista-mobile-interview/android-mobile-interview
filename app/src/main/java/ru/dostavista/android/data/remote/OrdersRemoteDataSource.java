package ru.dostavista.android.data.remote;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.dostavista.android.data.DataSource;
import ru.dostavista.android.data.Order;
import ru.dostavista.android.http.HttpClient;
import ru.dostavista.android.http.Request;
import ru.dostavista.android.http.Response;

public class OrdersRemoteDataSource implements DataSource<OrderParams, List<Order>> {

    private static String TAG = OrdersRemoteDataSource.class.getSimpleName();

    private HttpClient httpClient;

    private AsyncTask<OrderParams, Void, Void> task;

    public OrdersRemoteDataSource(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public void getData(OrderParams orderParams, final Callback<List<Order>> callback) {
        task = new OrderTask(httpClient, callback);
        task.execute(orderParams);
    }

    @Override
    public void dispose() {
        task.cancel(true);
    }

    private static class OrderTask extends AsyncTask<OrderParams, Void, Void> {

        private HttpClient httpClient;

        private Callback<List<Order>> callback;

        public OrderTask(HttpClient httpClient, Callback<List<Order>> callback) {
            this.httpClient = httpClient;
            this.callback = callback;
        }

        @Override
        protected Void doInBackground(OrderParams[] params) {
            if (params.length != 1 && !(params[0] instanceof OrderParams)) {
                String message = String.format(
                        "Wrong number of params: expected %d but was %d",
                        1,
                        params.length
                );
                throw new IllegalArgumentException(message);
            }
            OrderParams orderParams = params[0];
            Integer sinceId = orderParams.sinceId;
            Integer limit = orderParams.limit;
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
                    callback.onDataLoaded(newOrders);
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError();
                }
            } else {
                callback.onError();
            }
            return null;
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            Log.d(TAG, "Cancelling OrdersAsyncTask");
            httpClient = null;
            callback = null;
        }
    }

}
