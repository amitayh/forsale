package forsale.server.domain.typeadapter;

import com.google.gson.Gson;
import forsale.server.TestCase;
import forsale.server.domain.Sale;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class SaleTypeAdapterTest extends TestCase {

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

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
        Vendor vendor = new Vendor();
        vendor.setId(1);
        vendor.setName("Zara");

        Sale sale = new Sale();
        sale.setId(1);
        sale.setTitle("Sale #1");
        sale.setExtra("15% discount on all products!");
        sale.setVendor(vendor);
        sale.setStartDate(dateFormat.parse("2014-12-05"));
        sale.setEndDate(dateFormat.parse("2015-01-01"));

        String expected = "{" +
                "\"id\":1," +
                "\"title\":\"Sale #1\"," +
                "\"extra\":\"15% discount on all products!\"," +
                "\"vendor\":\"Zara\"," +
                "\"start\":\"2014-12-05\"," +
                "\"end\":\"2015-01-01\"" +
                "}";

        String actual = gson.toJson(sale);

        assertEquals(expected, actual);
    }

    @Test
    public void testNullExtraField() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setId(1);
        vendor.setName("Zara");

        Sale sale = new Sale();
        sale.setId(1);
        sale.setTitle("15% discount on all products!");
        sale.setVendor(vendor);
        sale.setStartDate(dateFormat.parse("2014-12-05"));
        sale.setEndDate(dateFormat.parse("2015-01-01"));

        String expected = "{" +
                "\"id\":1," +
                "\"title\":\"15% discount on all products!\"," +
                "\"vendor\":\"Zara\"," +
                "\"start\":\"2014-12-05\"," +
                "\"end\":\"2015-01-01\"" +
                "}";

        String actual = gson.toJson(sale);

        assertEquals(expected, actual);
    }

}