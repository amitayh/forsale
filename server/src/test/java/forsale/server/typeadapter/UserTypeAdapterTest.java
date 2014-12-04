package forsale.server.typeadapter;

import com.google.gson.Gson;
import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
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
    public void testSerialize() throws Exception {
        User user = new User();
        user.setId(1);
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        user.setBirthDath(new BirthDate("1940-10-09"));

        String expected = "{\"id\":1," +
                            "\"email\":\"john@beatles.com\"," +
                            "\"name\":\"John Lennon\"," +
                            "\"gender\":\"MALE\"," +
                            "\"birth\":\"1940-10-09\"}";

        String actual = gson.toJson(user);

        assertEquals(expected, actual);
    }

}