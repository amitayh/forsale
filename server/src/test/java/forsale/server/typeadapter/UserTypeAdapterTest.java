package forsale.server.typeadapter;

import com.google.gson.Gson;
import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.Password;
import forsale.server.domain.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class UserTypeAdapterTest extends TestCase {

    private Gson gson;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        gson = (Gson) container.get("gson");
    }

    @After
    public void tearDown() {
        gson = null;
    }

    @Test
    public void testSerialize() {
        User user = new User();
        user.setId(1);
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        Calendar calendar1 = new GregorianCalendar(1940, 10, 9);
        user.setBirthDath(calendar1.getTime());

        String expected = "{\"id\":1,\"email\":\"john@beatles.com\",\"name\":\"John Lennon\"}";
        String actual = gson.toJson(user);

        assertEquals(expected, actual);
    }

}