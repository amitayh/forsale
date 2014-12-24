package forsale.server.domain;

import forsale.server.domain.exception.ValidationException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BirthDateTest {

    @Test
    public void testGetTime() throws Exception {
        BirthDate birthDate = new BirthDate("2014-12-01");
        assertEquals(1417384800000L, birthDate.getTime());
    }

    @Test
    public void testToString() throws Exception {
        BirthDate birthDate = new BirthDate("2014-12-01");
        assertEquals("2014-12-01", birthDate.toString());
    }

    @Test(expected = ValidationException.class)
    public void testDateValidation() throws Exception {
        new BirthDate("foo");
    }

}