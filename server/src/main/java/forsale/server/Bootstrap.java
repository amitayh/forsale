package forsale.server;

import forsale.server.dependencyinjection.Container;
import forsale.server.service.provider.*;

import java.util.logging.Level;

public class Bootstrap {

    public static Container createDependencyInjectionContainer() {
        Container container = new Container();

        // Domain services
        container.set("service.auth", new AuthServiceProvider());
        container.set("service.vendors", new VendorsServiceProvider());
        container.set("service.sales", new SalesServiceProvider());
        container.set("service.users", new UsersServiceProvider());

        // MySQL connection
        container.set("mysql.db", "forsale");
        container.set("mysql.user", "root");
        container.set("mysql.password", "");
        container.set("mysql", new MysqlServiceProvider());

        // Redis connection
        container.set("redis", new RedisServiceProvider());

        // JSON serializer
        container.set("gson", new GsonServiceProvider());

        // Logger
        container.set("logger.name", "forsale");
        container.set("logger.level", Level.FINE);
        container.set("logger", new LoggerServiceProvider());

        return container;
    }

}
