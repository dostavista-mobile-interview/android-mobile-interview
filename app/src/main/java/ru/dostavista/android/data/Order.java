package ru.dostavista.android.data;

import org.json.JSONException;
import org.json.JSONObject;

public class Order {

    private String orderId;
    private String address;
    private String date;
    private String text;

    public Order(String orderId, String address, String date, String text) {
        this.orderId = orderId;
        this.address = address;
        this.date = date;
        this.text = text;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getAddress() {
        return address;
    }

    public String getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public static Order fromJson(JSONObject jsonObject) throws JSONException {

        String orderId = jsonObject.getString("order_id");
        String address = jsonObject.getString("address");
        String date = jsonObject.getString("date");
        String text = jsonObject.getString("text");

        return new Order(orderId, address, date, text);
    }
}
