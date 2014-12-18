package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.service.AuthService;
import redis.clients.jedis.Jedis;

public class AuthServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Jedis redis = (Jedis) container.get("redis");

        return new AuthService(redis);
    }

}
