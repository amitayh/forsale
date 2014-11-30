package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.service.VendorsService;

import java.sql.Connection;

public class VendorsServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Connection mysql = (Connection) container.get("mysql");

        return new VendorsService(mysql);
    }

}
