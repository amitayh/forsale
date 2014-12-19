package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.events.Dispatcher;
import forsale.server.service.SalesService;
import forsale.server.service.event.SaleViewEvent;
import forsale.server.service.listener.SaleViewListener;
import redis.clients.jedis.Jedis;

import java.sql.Connection;

public class SalesServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Connection mysql = (Connection) container.get("mysql");
        Jedis redis = (Jedis) container.get("redis");
        SalesService sales = new SalesService(mysql, redis);

        Dispatcher dispatcher = (Dispatcher) container.get("dispatcher");
        dispatcher.addListener(SaleViewEvent.class, new SaleViewListener(sales));

        return sales;
    }

}
