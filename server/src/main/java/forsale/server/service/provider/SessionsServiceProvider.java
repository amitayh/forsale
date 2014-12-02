package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;

import forsale.server.service.SessionsService;
import redis.clients.jedis.Jedis;

public class SessionsServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Jedis jedis = (Jedis)container.get("redis");

        return new SessionsService(jedis);
    }

}
