package forsale.server.ioc;

import java.util.HashMap;
import java.util.Map;

public class Container {

    final private Map<String, Object> values = new HashMap<>();

    public void set(String key, Object value) {
        values.put(key, value);
    }

    public Object get(String key, Object defaultValue) throws Exception {
        Object value = values.get(key);
        if (value == null) {
            value = defaultValue;
        } else if (value instanceof ServiceProvider) {
            value = ((ServiceProvider) value).create(this);
            values.put(key, value);
        }
        return value;
    }

    public Object get(String key) throws Exception {
        return get(key, null);
    }

}
