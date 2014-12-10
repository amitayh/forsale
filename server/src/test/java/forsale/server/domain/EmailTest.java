package forsale.server.domain;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class EmailTest {

    @Test
    public void testEquals() throws Exception {
        Email email1 = new Email("john@beatles.com");
        Email email2 = new Email("john@beatles.com");
        Email email3 = new Email("paul@beatles.com");

        assertEquals(email1, email2);
        assertNotEquals(email1, email3);
    }

    @Test
    public void testEmailConvertsToLowerCase() {
        Email email = new Email("FAKE@MAIL.com");
        assertEquals("fake@mail.com", email.toString());
    }

}