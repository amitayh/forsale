package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Vendor;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.*;

public class VendorsServiceTest extends TestCase {

    private VendorsServiceInterface vendors;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection) container.get("mysql"));

        vendors = (VendorsServiceInterface) container.get("service.vendors");
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