package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.service.SalesService;
import redis.clients.jedis.Jedis;

import java.sql.Connection;

public class SalesServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Connection mysql = (Connection) container.get("mysql");
        Jedis redis = (Jedis) container.get("redis");

        return new SalesService(mysql, redis);
    }

}
