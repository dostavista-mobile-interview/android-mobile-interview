package data;

import ru.dostavista.android.data.OrdersRepository;
import ru.dostavista.android.data.remote.OrdersRemoteDataSource;
import ru.dostavista.android.http.HttpClient;

public class Injection {

    public static OrdersRepository provideOrdersRepository() {
        return OrdersRepository.getInstance(new OrdersRemoteDataSource(new HttpClient()));
    }
}
