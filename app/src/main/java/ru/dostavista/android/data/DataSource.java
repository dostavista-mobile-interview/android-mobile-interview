package ru.dostavista.android.data;

import ru.dostavista.android.util.Disposable;

public interface DataSource<P, R> extends Disposable {

    interface Callback<T> {

        void onDataLoaded(T data);

        void onError();
    }

    void getData(P params, Callback<R> callback);

}
