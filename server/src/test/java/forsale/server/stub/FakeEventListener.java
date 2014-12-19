package forsale.server.stub;

import forsale.server.events.Listener;

public class FakeEventListener implements Listener<FakeEvent> {

    final private String name;

    public FakeEventListener(String name) {
        this.name = name;
    }

    @Override
    public void dispatch(FakeEvent event) {
        event.call(name);
    }

}
