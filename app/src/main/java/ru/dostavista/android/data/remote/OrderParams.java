package ru.dostavista.android.data.remote;

public final class OrderParams {

    public final Integer sinceId;

    public final Integer limit;

    public OrderParams(Integer sinceId, Integer limit) {
        this.sinceId = sinceId;
        this.limit = limit;
    }

}
