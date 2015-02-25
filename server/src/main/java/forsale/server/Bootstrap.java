package forsale.server;

import forsale.server.ioc.Container;
import forsale.server.service.provider.*;

import java.util.logging.Level;

public class Bootstrap {

    public static Container createIocContainer() {
        Container container = new Container();

        // Events dispatcher
        container.set("dispatcher", new EventsDispatcherProvider());

        // Domain services
        container.set("service.auth", new AuthServiceProvider());
        container.set("service.vendors", new VendorsServiceProvider());
        container.set("service.sales", new SalesServiceProvider());
        container.set("service.users", new UsersServiceProvider());
        container.set("service.db-builder", new DbBuilderServiceProvider());

        // MySQL connection
        container.set("mysql.db", "forsale");
        container.set("mysql.user", "root");
        container.set("mysql.password", "");
        container.set("mysql.auto-reconnect", true);
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
