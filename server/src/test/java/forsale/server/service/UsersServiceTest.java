package forsale.server.service;

import com.google.gson.Gson;
import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.text.SimpleDateFormat;

import static org.junit.Assert.*;

public class UsersServiceTest extends TestCase {

    private UsersServiceInterface usersService;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection)container.get("mysql"));

        this.usersService = (UsersServiceInterface)container.get("service.users");
    }

    @After
    public void tearDown() {
        this.usersService = null;
    }

    @Test
    public void testUserInsertedSameAsUserGotById() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // prepare new user
        User user = new User();
        user.setId(-1); // dummy id
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate(dateFormat.parse("1940-10-09").getTime()));

        // insert user
        int userId = usersService.insert(user);
        user.setId(userId); // update real id

        // validate user id is positive
        assertTrue(userId >= 0);

        // get the inserted user by id
        User insertedUser = usersService.get(userId);

        // validate same user
        assertNotNull(insertedUser);
        assertEquals(user, insertedUser);
    }

    @Test
    public void testUserEditSucceed() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        // prepare new user
        User user = new User();
        user.setId(-1); // dummy id
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate(dateFormat.parse("1940-10-09").getTime()));

        // insert user
        int userId = usersService.insert(user);
        user.setId(userId); // update real id

        assertTrue(userId >= 0);

        // edit user name
        user.setName("Ringo Starr");
        usersService.edit(user);

        // get edited user
        User editedUser = usersService.get(userId);

        assertNotNull(editedUser);
        assertEquals(user, editedUser);
    }

}