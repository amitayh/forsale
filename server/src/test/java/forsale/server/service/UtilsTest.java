package forsale.server.service;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void getGetMultipleParametersList() {
        List<Integer> ids = new ArrayList<>();
        ids.add(1);
        ids.add(2);
        ids.add(4);
        ids.add(7);

        String expected = "(?, ?, ?, ?)";
        assertEquals(expected, Utils.getMultipleParametersList(ids));
    }

}