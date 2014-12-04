package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.Sale;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SalesServiceTest extends TestCase {

    private SalesService sales;

    private Vendor vendor;

    @Before
    public void setUp() throws Exception {
        flushMysql();
        flushRedis();

        sales = new SalesService(getMysql(), getRedis());
        vendor = createVendor();
    }

    @After
    public void tearDown() {
        sales = null;
        vendor = null;
    }

    @Test
    public void testInsertSale() throws Exception {
        Sale sale = new Sale();
        sale.setTitle("Sale #1");
        sale.setVendor(vendor);
        int saleId = sales.insert(sale);

        assertEquals(saleId, sale.getId());
        assertNotNull(saleId);
        assertTrue(saleId > 0);
    }

    @Test
    public void testGetSalesByIdWithEmptySet() throws Exception {
        Sale sale1 = new Sale();
        sale1.setTitle("Sale #1");
        sale1.setVendor(vendor);
        sales.insert(sale1);

        Set<Integer> ids = new HashSet<>();

        List<Sale> result = sales.getSalesById(ids);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSalesById() throws Exception {
        // Insert sales
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

        Set<Integer> ids = new HashSet<>();
        ids.add(sale1.getId());
        ids.add(sale3.getId());

        List<Sale> result = sales.getSalesById(ids);
        assertEquals(2, result.size());
        assertEquals(sale1.getId(), result.get(0).getId());
        assertEquals(sale3.getId(), result.get(1).getId());
    }

    @Test
    public void testGetPopularReturnsSalesSortedByPopularity() throws Exception {
        // Insert sales
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

        // Set view count rank: sale2, sale3, sale1
        sales.increaseViewCount(sale1);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale3);
        sales.increaseViewCount(sale3);

        List<Sale> popular = sales.getPopular();
        assertEquals(sale2.getId(), popular.get(0).getId()); // 1st
        assertEquals(sale3.getId(), popular.get(1).getId()); // 2nd
        assertEquals(sale1.getId(), popular.get(2).getId()); // 3rd
    }

    private Vendor createVendor() throws Exception {
        VendorsServiceInterface vendors = (VendorsServiceInterface) container.get("service.vendors");
        Vendor vendor = new Vendor();
        vendor.setName("Vendor #1");
        vendors.insert(vendor);

        return vendor;
    }

}