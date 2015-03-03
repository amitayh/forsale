package forsale.server.service.provider;

import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;

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
        Level level = (Level)container.get("logger.level");
        logger.setLevel(level);
        return logger;
    }

}
