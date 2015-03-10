package forsale.server.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class JsonResultTest {

    @Test
    public void testSuccess() throws Exception {
        JsonResult result = new JsonResult();
        result.success("Success data");
        assertEquals(200, result.status);
        assertEquals("Success data", result.data);
        assertNull(result.error);
    }

    @Test
    public void testFail() throws Exception {
        JsonResult result = new JsonResult();
        result.fail("Error message");
        assertEquals(400, result.status);
        assertEquals("Error message", result.error);
        assertNull(result.data);
    }
    
}