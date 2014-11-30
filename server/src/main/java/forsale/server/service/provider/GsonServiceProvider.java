package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;

public class GsonServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        return new com.google.gson.Gson();
    }

}
