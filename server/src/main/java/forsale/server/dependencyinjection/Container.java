package forsale.server.dependencyinjection;

import java.util.HashMap;
import java.util.Map;

public class Container {

    final private Map<String, Object> values = new HashMap<>();

    public void set(String key, Object value) {
        values.put(key, value);
    }

    public Object get(String key) throws Exception {
        Object value = values.get(key);
        if (value instanceof ServiceFactory) {
            value = ((ServiceFactory) value).create(this);
            values.put(key, value);
        }
        return value;
    }

}
