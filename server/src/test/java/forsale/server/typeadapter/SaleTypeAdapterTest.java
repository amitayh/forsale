package forsale.server.typeadapter;

import com.google.gson.Gson;
import forsale.server.TestCase;
import forsale.server.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SaleTypeAdapterTest extends TestCase {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        gson = (Gson) container.get("gson");
    }

    @After
    public void tearDown() {
        gson = null;
    }

    @Test
    public void testSerialize() throws Exception {
        Sale sale = new Sale();
        sale.setId(1);
        sale.setTitle("Sale #1");
        sale.setExtra("15% discount on all products!");

        String expected = "{\"id\":1,\"title\":\"Sale #1\"}";

        String actual = gson.toJson(sale);

        assertEquals(expected, actual);
    }
}