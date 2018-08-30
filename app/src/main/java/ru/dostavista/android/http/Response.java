package ru.dostavista.android.http;

import org.json.JSONException;
import org.json.JSONObject;

public class Response {

    private boolean success;
    private int statusCode;
    private JSONObject json;
    private String error;

    public Response(int statusCode, String responseString) throws JSONException {
        this.statusCode = statusCode;
        this.json = new JSONObject(responseString);
        this.success = statusCode == 200;
    }

    public Response(String error) {
        this.error = error;
    }

    public boolean isSuccess() {
        return success;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public JSONObject getJson() {
        return json;
    }

    public String getError() {
        return error;
    }
}
