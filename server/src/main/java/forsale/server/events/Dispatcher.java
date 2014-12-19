package forsale.server.events;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Dispatcher {

    private Multimap<Class, Listener> events = LinkedListMultimap.create();

    public boolean addListener(Class eventType, Listener listener) {
        return events.put(eventType, listener);
    }

    public void dispatch(Object event) {
        for (Listener listener : events.get(event.getClass())) {
            listener.dispatch(event);
        }
    }

}
