package forsale.server.domain;

import forsale.server.service.Utils;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class PasswordTest {

    @Test
    public void testPasswordAddsSalt() throws Exception {
        String original = "t0p-s3cr3t";
        Password password = new Password(original);
        assertNotEquals(Utils.md5(original), password.getHashedPassword());
    }

    @Test
    public void testEquals() throws Exception {
        Password password1 = new Password("t0p-s3cr3t");
        Password password2 = new Password("t0p-s3cr3t");
        Password password3 = new Password("123456");

        assertEquals(password1, password2);
        assertNotEquals(password1, password3);
    }


}