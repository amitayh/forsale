package forsale.server.domain.typeadapter;

import com.google.gson.*;
import forsale.server.domain.Sale;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;

public class SaleTypeAdapter implements JsonSerializer<Sale> {

    final static private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public JsonElement serialize(Sale sale, Type type, JsonSerializationContext context) {
        JsonObject el = new JsonObject();
        el.add("id", new JsonPrimitive(sale.getId()));
        el.add("title", new JsonPrimitive(sale.getTitle()));
        el.add("extra", new JsonPrimitive(sale.getExtra()));
        el.add("vendor", new JsonPrimitive(sale.getVendor().getName()));
        el.add("start", new JsonPrimitive(dateFormat.format(sale.getStartDate())));
        el.add("end", new JsonPrimitive(dateFormat.format(sale.getEndDate())));
        return el;
    }

}
