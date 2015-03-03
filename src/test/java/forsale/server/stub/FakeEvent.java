package forsale.server.stub;

import java.util.ArrayList;
import java.util.List;

public class FakeEvent {

    final private List<String> callers = new ArrayList<>();

    public void call(String caller) {
        callers.add(caller);
    }

    public List<String> getCallers() {
        return callers;
    }

}
