package forsale.server.service.provider;

import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;
import forsale.server.events.Dispatcher;

public class EventsDispatcherProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        return new Dispatcher();
    }

}
