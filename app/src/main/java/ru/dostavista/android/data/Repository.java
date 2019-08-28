package ru.dostavista.android.data;

import java.util.List;

public interface Repository<T> {

    interface RepositoryCallback<T> {

        void onOrdersLoaded(List<T> orders, boolean isNextPage);

        void onOrdersNotAvailable();

    }

    void getData(Integer sinceId, RepositoryCallback<T> repositoryCallback);

}
