package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;

import java.util.logging.Logger;

public class LoggerServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        String name = (String) container.get("logger.name");

        return Logger.getLogger(name);
    }

}
