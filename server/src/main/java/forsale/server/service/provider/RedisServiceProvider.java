package forsale.server.service.provider;

import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;
import redis.clients.jedis.Jedis;

public class RedisServiceProvider implements ServiceProvider {

    private static final String DEFAULT_HOST = "localhost";

    private static final int DEFAULT_PORT = 6379;

    private static final int DEFAULT_DB = 0;

    @Override
    public Object create(Container container) throws Exception {
        String host = (String) container.get("redis.host", DEFAULT_HOST);
        int port = (int) container.get("redis.port", DEFAULT_PORT);
        int db = (int) container.get("redis.db", DEFAULT_DB);
        Jedis redis = new Jedis(host, port);
        redis.select(db);

        return redis;
    }

}
