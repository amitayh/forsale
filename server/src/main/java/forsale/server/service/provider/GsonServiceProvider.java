package forsale.server.service.provider;

import com.google.gson.GsonBuilder;
import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.domain.User;
import forsale.server.typeadapter.UserTypeAdapter;

public class GsonServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        GsonBuilder gson = new GsonBuilder();
        gson.registerTypeAdapter(User.class, new UserTypeAdapter());

        return gson.create();
    }

}
