package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsersServiceTest extends TestCase {

    private UsersService users;

    @Before
    public void setUp() throws Exception {
        flushMysql();

        users = new UsersService(getMysql());
    }

    @After
    public void tearDown() {
        users = null;
    }

    @Test
    public void testUserInsertedSameAsUserGotById() throws Exception {
        // prepare new user
        User user = new User();
        user.setId(-1); // dummy id
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("09-10-1940"));

        // insert user
        int userId = users.insert(user);
        user.setId(userId); // update real id

        // validate user id is positive
        assertTrue(userId >= 0);

        // get the inserted user by id
        User insertedUser = users.get(userId);

        // validate same user
        assertNotNull(insertedUser);
        assertEquals(user, insertedUser);
    }

    @Test
    public void testUserEditSucceed() throws Exception {
        // prepare new user
        User user = new User();
        user.setId(-1); // dummy id
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("09-10-1940"));

        // insert user
        int userId = users.insert(user);
        user.setId(userId); // update real id

        assertTrue(userId >= 0);

        // edit user name
        user.setName("Ringo Starr");
        users.edit(user);

        // get edited user
        User editedUser = users.get(userId);

        assertNotNull(editedUser);
        assertEquals(user, editedUser);
    }

}