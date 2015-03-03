package forsale.server.service.provider;

import com.google.gson.GsonBuilder;
import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;
import forsale.server.domain.Sale;
import forsale.server.domain.User;
import forsale.server.domain.typeadapter.SaleTypeAdapter;
import forsale.server.domain.typeadapter.UserTypeAdapter;

public class GsonServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserTypeAdapter());
        gson.registerTypeAdapter(Sale.class, new SaleTypeAdapter());

        return gson.create();
    }

}
