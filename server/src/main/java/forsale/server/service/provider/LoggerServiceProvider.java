package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerServiceProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        String name = (String) container.get("logger.name");
        Logger logger = Logger.getLogger(name);
        /*
        // Enabling logging at a given level also enables logging at all higher levels.
        SEVERE (highest value)
        WARNING
        INFO
        CONFIG
        FINE
        FINER
        FINEST (lowest value)
         */
        logger.setLevel(Level.FINE);
        return logger;
    }

}
