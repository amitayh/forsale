package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import forsale.server.service.exception.DuplicateEmailException;
import forsale.server.service.exception.InvalidCredentialsException;
import forsale.server.service.exception.MissingUserException;
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
        user.setBirthDath(new BirthDate("1940-10-09"));

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

    @Test(expected=DuplicateEmailException.class)
    public void testCantInsertTwoUsersWithSameEmail() throws Exception {
        // 1st user
        User user1 = new User();
        user1.setName("John Lennon");
        user1.setEmail(new Email("john@beatles.com"));
        user1.setGender(Gender.MALE);
        user1.setPassword(new Password("123"));
        user1.setBirthDath(new BirthDate("2014-10-09"));

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        user2.setBirthDath(new BirthDate("2014-10-09"));

        // Signup users - should throw an exception
        users.insert(user1);
        users.insert(user2);
    }

    @Test(expected = MissingUserException.class)
    public void testGetUserByIdFailure() throws Exception {
        users.get(1);
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));

        int userId = users.insert(user);

        User fetchedUser = users.get(userId);
        assertEquals(user, fetchedUser);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testGetUserByCredentialsFailure() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");
        users.get(new User.Credentials(email, password));
    }

    @Test
    public void testGetUserByCredentialsSuccess() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("2014-10-09"));
        users.insert(user);

        User fetchedUser = users.get(new User.Credentials(email, password));
        assertEquals(user, fetchedUser);
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
        user.setBirthDath(new BirthDate("1940-10-09"));

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