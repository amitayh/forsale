package forsale.server;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.service.HelloService;
import forsale.server.service.provider.*;

public class Bootstrap {

    public static Container createDependencyInjectionContainer() {
        Container container = new Container();

        // Example service initialization - using simple parameters and a service provider
        container.set("hello.prefix", "Message: ");
        container.set("hello.suffix", "!");
        container.set("service.hello", new ServiceProvider() {
            @Override
            public Object create(Container container) throws Exception {
                String prefix = (String) container.get("hello.prefix");
                String suffix = (String) container.get("hello.suffix");
                return new HelloService(prefix, suffix);
            }
        });

        // Domain services
        container.set("service.vendors", new VendorsServiceProvider());
        container.set("service.sales", new SalesServiceProvider());

        // MySQL connection
        container.set("mysql.db", "forsale");
        container.set("mysql.user", "root");
        container.set("mysql.password", "");
        container.set("mysql", new MysqlServiceProvider());

        // Redis connection
        container.set("redis", new RedisServiceProvider());

        // JSON serializer
        container.set("gson", new GsonServiceProvider());

        return container;
    }

}
