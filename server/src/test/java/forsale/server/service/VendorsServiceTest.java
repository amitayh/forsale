package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.Vendor;
import forsale.server.service.exception.MissingVendorException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class VendorsServiceTest extends TestCase {

    private VendorsService vendors;

    @Before
    public void setUp() throws Exception {
        flushMysql();

        vendors = new VendorsService(getMysql());
    }

    @After
    public void tearDown() {
        vendors = null;
    }

    @Test
    public void testInsertVendor() throws Exception {
        Vendor vendor = createVendor("Vendor #1");

        int vendorId = vendor.getId();
        assertNotNull(vendorId);
        assertTrue(vendorId > 0);
    }

    @Test(expected = MissingVendorException.class)
    public void testGetMissingVendorThrowsException() throws Exception {
        vendors.get(-1);
    }

    @Test
    public void testGetVendorById() throws Exception {
        Vendor vendor = createVendor("Vendor #1");

        Vendor fetchedVendor = vendors.get(vendor.getId());
        assertEquals(vendor, fetchedVendor);
    }

    @Test
    public void testGetVendorsByIdWithEmptySet() throws Exception {
        createVendor("Sale #1");

        Set<Integer> ids = new HashSet<>();

        List<Vendor> result = vendors.get(ids);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSalesById() throws Exception {
        // Insert sales
        Vendor vendor1 = createVendor("Vendor #1");
        Vendor vendor2 = createVendor("Vendor #2");
        Vendor vendor3 = createVendor("Vendor #3");

        Set<Integer> ids = new HashSet<>();
        ids.add(vendor1.getId());
        ids.add(vendor3.getId());

        List<Vendor> result = vendors.get(ids);
        assertEquals(2, result.size());
        assertEquals(vendor1, result.get(0));
        assertEquals(vendor3, result.get(1));
    }

    @Test(expected = MissingVendorException.class)
    public void testGetSalesByIdMissingSale() throws Exception {
        // Insert sales
        Vendor vendor1 = createVendor("Vendor #1");
        Vendor vendor2 = createVendor("Vendor #2");
        Vendor vendor3 = createVendor("Vendor #3");

        Set<Integer> ids = new HashSet<>();
        ids.add(vendor1.getId());
        ids.add(vendor2.getId());
        ids.add(vendor3.getId() + 1); // Invalid ID

        vendors.get(ids);
    }

    private Vendor createVendor(String name) throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendors.insert(vendor);

        return vendor;
    }

}