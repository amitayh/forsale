package forsale.server.domain;

import forsale.server.domain.exception.ValidationException;
import org.junit.Test;

import static org.junit.Assert.*;

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
    public void testEmailConvertsToLowerCase() throws ValidationException {
        Email email = new Email("FAKE@MAIL.com");
        assertEquals("fake@mail.com", email.toString());
    }

    @Test
    public void testEmailValidation() {
        String[] invalidEmails = new String[]{null, "", "invalid-email"};

        for (String email : invalidEmails) {
            try {
                new Email(email);
                fail("Instantiated Email with invalid address '" + email + "'");
            } catch (ValidationException ignored) {}
        }
    }

}