package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.service.AuthService;
import forsale.server.service.UsersServiceInterface;

public class AuthServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        UsersServiceInterface usersService = (UsersServiceInterface)container.get("service.users");
        return new AuthService(usersService);
    }

}
