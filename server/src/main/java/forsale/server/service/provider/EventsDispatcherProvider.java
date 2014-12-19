package forsale.server.service.provider;

import forsale.server.dependencyinjection.Container;
import forsale.server.dependencyinjection.ServiceProvider;
import forsale.server.events.Dispatcher;

public class EventsDispatcherProvider implements ServiceProvider {

    @Override
    public Object create(Container container) throws Exception {
        return new Dispatcher();
    }

}
