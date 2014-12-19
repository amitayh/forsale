package forsale.server.events;

public interface Listener<T> {

    public void dispatch(T event);

}
