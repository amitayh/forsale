package forsale.server.service;

import org.junit.Test;

import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void getGetMultipleParametersList() {
        assertEquals("(?)", Utils.getMultipleParametersList(1));
        assertEquals("(?, ?, ?, ?)", Utils.getMultipleParametersList(4));
    }

    @Test
    public void testMd5() throws Exception {
        assertEquals("5eb63bbbe01eeed093cb22bb8f5acdc3", Utils.md5("hello world"));
    }

    @Test(expected = Exception.class)
    public void testGetResourceThrowsExceptionIfNotFound() throws Exception {
        Utils.getResource("fake/resource");
    }

    @Test
    public void testGetResourceReturnsValidURL() throws Exception {
        URL resource = Utils.getResource("db/schema.sql");
        assertTrue(resource.getPath().endsWith("db/schema.sql"));
    }

}