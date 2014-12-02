package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Sale;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class SalesServiceTest extends TestCase {

    private VendorsServiceInterface vendors;

    private SalesServiceInterface sales;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection) container.get("mysql"));
        flush((Jedis) container.get("redis"));

        vendors = (VendorsServiceInterface) container.get("service.vendors");
        sales = (SalesServiceInterface) container.get("service.sales");
    }

    @After
    public void tearDown() {
        vendors = null;
        sales = null;
    }

    @Test
    public void testInsertSale() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName("Vendor #1");
        vendors.insert(vendor);

        Sale sale = new Sale();
        sale.setTitle("Sale #1");
        sale.setVendor(vendor);
        int saleId = sales.insert(sale);

        assertEquals(saleId, sale.getId());
        assertNotNull(saleId);
        assertTrue(saleId > 0);
    }

    @Test
    public void testGetPopularReturnsSalesSortedByPopularity() throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName("Vendor #1");
        vendors.insert(vendor);

        Sale sale1 = new Sale();
        sale1.setTitle("Sale #1");
        sale1.setVendor(vendor);
        sales.insert(sale1);

        Sale sale2 = new Sale();
        sale2.setTitle("Sale #1");
        sale2.setVendor(vendor);
        sales.insert(sale2);

        Sale sale3 = new Sale();
        sale3.setTitle("Sale #1");
        sale3.setVendor(vendor);
        sales.insert(sale3);
    }

}