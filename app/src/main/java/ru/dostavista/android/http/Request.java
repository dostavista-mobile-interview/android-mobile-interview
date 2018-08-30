package ru.dostavista.android.http;

public class Request {

    private String path;

    public Request(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
