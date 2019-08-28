package ru.dostavista.android.data;

import ru.dostavista.android.util.Disposable;

public interface Repository<P, T> extends Disposable {

    interface RepositoryCallback<T> {

        void onOrdersLoaded(T data, boolean isNextPage);

        void onError();

    }

    void getData(P params, RepositoryCallback<T> repositoryCallback);

}
