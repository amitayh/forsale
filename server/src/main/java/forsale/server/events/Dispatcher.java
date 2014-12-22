package forsale.server.events;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class Dispatcher {

    private final Multimap<Class, Listener> events = LinkedListMultimap.create();

    public <T> boolean addListener(Class<T> eventType, Listener<T> listener) {
        return events.put(eventType, listener);
    }

    public void dispatch(Object event) {
        for (Listener listener : events.get(event.getClass())) {
            //noinspection unchecked
            listener.dispatch(event);
        }
    }

}
