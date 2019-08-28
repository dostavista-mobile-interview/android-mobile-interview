package ru.dostavista.android.http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import ru.dostavista.android.util.InputStreamUtils;

public class HttpClient {

    private static String API_URL = "https://externalwebhooks.dostavista.net/";
    private static final int DEFAULT_CONNECTION_TIMEOUT = 15000;
    private static final int DEFAULT_READ_TIMEOUT = 60000;

    public Response execute(Request request) {
        String url = API_URL + request.getPath();

        InputStream input = null;
        OutputStream ostream = null;
        try {

            HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
            connection.setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT);
            connection.setReadTimeout(DEFAULT_READ_TIMEOUT);
            connection.setDoInput(true);
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                String responseString = InputStreamUtils.toString(connection.getInputStream());
                return new Response(responseCode, responseString);
            } else if (responseCode == 400) {
                String responseString = InputStreamUtils.toString(connection.getErrorStream());
                return new Response(responseCode, responseString);
            } else {
                return new Response(500, null);
            }
        } catch (Exception ignored) {
            return new Response("unknown error");
        } finally {
            try {
                if (ostream != null)
                    ostream.close();
                if (input != null)
                    input.close();
            } catch (Exception ignore) { /* ignore */ }
        }
    }
}
