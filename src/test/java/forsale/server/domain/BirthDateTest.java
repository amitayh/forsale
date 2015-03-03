package forsale.server.domain;

import forsale.server.domain.exception.ValidationException;
import org.junit.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class BirthDateTest {

    @Test
    public void testGetTime() throws Exception {
        String dateString = "2014-12-01";
        BirthDate birthDate = new BirthDate(dateString);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        assertEquals(dateString, dateFormat.format(birthDate.getTime()));
    }

    @Test
    public void testToString() throws Exception {
        String dateString = "2014-12-01";
        BirthDate birthDate = new BirthDate(dateString);
        assertEquals(dateString, birthDate.toString());
    }

    @Test(expected = ValidationException.class)
    public void testDateValidation() throws Exception {
        new BirthDate("foo");
    }

}