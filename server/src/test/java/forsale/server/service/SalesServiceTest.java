package forsale.server.service;

import forsale.server.Bootstrap;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Sale;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;

public class SalesServiceTest {

    public static final String TEST_DB_MYSQL = "forsale_test";

    public static final int TEST_DB_REDIS = 1;

    private Container container;

    @Before
    public void setUp() throws Exception {
        container = getTestContainer();
        flush((Connection) container.get("mysql"));
        flush((Jedis) container.get("redis"));
    }

    @After
    public void tearDown() {
        container = null;
    }

    @Test
    public void testInsertSale() throws Exception {
        Container container = getTestContainer();
        VendorsServiceInterface vendors = (VendorsServiceInterface) container.get("service.vendors");
        SalesServiceInterface sales = (SalesServiceInterface) container.get("service.sales");

        Vendor vendor = new Vendor();
        vendor.setName("Vendor #1");
        vendors.insert(vendor);

        Sale sale1 = new Sale();
        sale1.setTitle("Sale #1");
        sale1.setVendor(vendor);
        sales.insert(sale1);

        Sale sale2 = new Sale();
        sale2.setTitle("Sale #2");
        sale2.setVendor(vendor);
        sales.insert(sale2);

        Sale sale3 = new Sale();
        sale3.setTitle("Sale #3");
        sale3.setVendor(vendor);
        sales.insert(sale3);

        assertEquals(1, sale1.getId());
        assertEquals(2, sale2.getId());
        assertEquals(3, sale3.getId());
    }

    @Test
    public void testGetPopularReturnsSalesSortedByPopularity() {
        // insert sales

        // insert views

        // get popular
    }

    private Container getTestContainer() throws Exception {
        Container container = Bootstrap.createDependencyInjectionContainer();
        container.set("mysql.db", TEST_DB_MYSQL);
        container.set("redis.db", TEST_DB_REDIS);
        return container;
    }

    private void flush(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("TRUNCATE TABLE sales;");
    }

    private void flush(Jedis redis) {
        redis.flushAll();
    }

}