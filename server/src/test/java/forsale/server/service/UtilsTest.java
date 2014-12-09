package forsale.server.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

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

}