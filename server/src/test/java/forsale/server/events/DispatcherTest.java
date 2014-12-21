package forsale.server.events;

import forsale.server.stub.FakeEvent;
import forsale.server.stub.FakeEventListener;
import forsale.server.stub.FakeStringListener;
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
        Listener<FakeEvent> listener = new FakeEventListener("listener");
        FakeEvent event = new FakeEvent();

        dispatcher.addListener(FakeEvent.class, listener);
        dispatcher.dispatch(event);

        List<String> callers = event.getCallers();
        assertEquals(1, callers.size());
        assertEquals("listener", callers.get(0));
    }

    @Test
    public void testAddMultipleListeners() {
        FakeEvent event = new FakeEvent();

        dispatcher.addListener(FakeEvent.class, new FakeEventListener("listener1"));
        dispatcher.addListener(FakeEvent.class, new FakeEventListener("listener2"));
        dispatcher.addListener(String.class, new FakeStringListener());
        dispatcher.dispatch(event);

        List<String> callers = event.getCallers();
        assertEquals(2, callers.size());
        assertEquals("listener1", callers.get(0));
        assertEquals("listener2", callers.get(1));
    }

    @Test
    public void testDispatchEventWithNoListeners() {
        dispatcher.dispatch(new FakeEvent());
    }

}