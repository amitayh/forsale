package forsale.server.service.provider;

import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;
import forsale.server.service.UsersService;

import java.sql.Connection;

public class UsersServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        Connection mysql = (Connection)container.get("mysql");

        return new UsersService(mysql);
    }
}
