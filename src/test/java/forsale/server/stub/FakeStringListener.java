package forsale.server.stub;

import forsale.server.events.Listener;

public class FakeStringListener implements Listener<String> {

    @Override
    public void dispatch(String event) {
        // Do nothing...
    }

}
