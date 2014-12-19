package forsale.server.events;

import forsale.server.stub.FakeEvent;
import forsale.server.stub.FakeEventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DispatcherTest {

    private Dispatcher dispatcher;

    @Before
    public void setUp() throws Exception {
        dispatcher = new Dispatcher();
    }

    @After
    public void tearDown() throws Exception {
        dispatcher = null;
    }

    @Test
    public void testAddListener() {
        Listener listener = new FakeEventListener("listener");
        FakeEvent event = new FakeEvent();

        dispatcher.addListener(FakeEvent.class, listener);
        dispatcher.dispatch(event);

        List<String> callers = event.getCallers();
        assertEquals(1, callers.size());
        assertEquals("listener", callers.get(0));
    }

    @Test
    public void testAddMultipleListeners() {
        Listener listener1 = new FakeEventListener("listener1");
        Listener listener2 = new FakeEventListener("listener2");
        Listener listener3 = new FakeEventListener("listener3");
        FakeEvent event = new FakeEvent();

        dispatcher.addListener(FakeEvent.class, listener1);
        dispatcher.addListener(String.class, listener2);
        dispatcher.addListener(FakeEvent.class, listener3);
        dispatcher.dispatch(event);

        List<String> callers = event.getCallers();
        assertEquals(2, callers.size());
        assertEquals("listener1", callers.get(0));
        assertEquals("listener3", callers.get(1));
    }

    @Test
    public void testDispatchEventWithNoListeners() {
        dispatcher.dispatch(new FakeEvent());
    }

}