package forsale.server;

import com.google.gson.Gson;
import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceFactory;
import forsale.server.service.HelloService;
import forsale.server.service.SalesService;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.DriverManager;

public class Bootstrap {

    public static Container createDependencyInjectionContainer() {
        Container container = new Container();

        // Example service initialization - using simple parameters and a service factory
        container.set("hello.prefix", "Message: ");
        container.set("hello.suffix", "!");
        container.set("service.hello", new ServiceFactory() {
            @Override
            public Object create(Container container) throws Exception {
                String prefix = (String) container.get("hello.prefix");
                String suffix = (String) container.get("hello.suffix");
                return new HelloService(prefix, suffix);
            }
        });

        // Sales service
        container.set("service.sales", new ServiceFactory() {
            @Override
            public Object create(Container container) throws Exception {
                Connection mysql = (Connection) container.get("mysql");
                Jedis redis = (Jedis) container.get("redis");
                return new SalesService(mysql, redis);
            }
        });

        // MySQL connection
        container.set("mysql.url", "jdbc:mysql://localhost:3306/forsale");
        container.set("mysql.user", "root");
        container.set("mysql.password", "");
        container.set("mysql", new ServiceFactory() {
            @Override
            public Object create(Container container) throws Exception {
                Class.forName("com.mysql.jdbc.Driver");
                String url = (String) container.get("mysql.url");
                String user = (String) container.get("mysql.user");
                String password = (String) container.get("mysql.password");
                return DriverManager.getConnection(url, user, password);
            }
        });

        // Redis connection
        container.set("redis.host", "localhost");
        container.set("redis.port", 6379);
        container.set("redis", new ServiceFactory() {
            @Override
            public Object create(Container container) throws Exception {
                String host = (String) container.get("redis.host");
                int port = (int) container.get("redis.port");
                return new Jedis(host, port);
            }
        });

        // JSON serializer
        container.set("gson", new ServiceFactory() {
            @Override
            public Object create(Container container) throws Exception {
                return new Gson();
            }
        });

        return container;
    }

}
