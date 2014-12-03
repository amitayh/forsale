package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
        Vendor vendor = new Vendor();
        vendor.setName("Vendor #1");
        int vendorId = vendors.insert(vendor);

        assertEquals(vendorId, vendor.getId());
        assertNotNull(vendorId);
        assertTrue(vendorId > 0);
    }

}