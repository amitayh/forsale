package forsale.server.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UtilsTest {

    @Test
    public void getGetMultipleParametersList() {
        assertEquals("(?)", Utils.getMultipleParametersList(1));
        assertEquals("(?, ?, ?, ?)", Utils.getMultipleParametersList(4));
    }

}