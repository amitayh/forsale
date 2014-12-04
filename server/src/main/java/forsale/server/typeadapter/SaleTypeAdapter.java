package forsale.server.typeadapter;

import com.google.gson.*;
import forsale.server.domain.Sale;

import java.lang.reflect.Type;

public class SaleTypeAdapter implements JsonSerializer<Sale> {

    @Override
    public JsonElement serialize(Sale sale, Type type, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.add("id", new JsonPrimitive(sale.getId()));
        el.add("title", new JsonPrimitive(sale.getTitle()));
        return el;
    }

}
