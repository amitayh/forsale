package forsale.server.domain.typeadapter;

import com.google.gson.*;
import forsale.server.domain.User;

import java.lang.reflect.Type;

public class UserTypeAdapter implements JsonSerializer<User> {

    @Override
    public JsonElement serialize(User user, Type type, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.add("id", new JsonPrimitive(user.getId()));
        el.add("email", new JsonPrimitive(user.getEmail().toString()));
        el.add("name", new JsonPrimitive(user.getName()));
        el.add("gender", new JsonPrimitive(user.getGender().toString()));
        el.add("birth", new JsonPrimitive(user.getBirthDate().toString()));
        return el;
    }

}
