package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.*;
import forsale.server.service.exception.DuplicateEmailException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class AuthServiceTest extends TestCase {

    private AuthServiceInterface auth;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection)container.get("mysql")); // clear db rows

        auth = (AuthServiceInterface)container.get("service.auth");
    }

    @After
    public void tearDown() {
        auth = null;
    }

    @Test
    public void testSignupSavesUser() throws Exception {
        // prepare new user
        User user = new User();
        user.setName("Assaf Grimberg");
        user.setEmail(new Email("assafgrim@gmail.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("AD#ri493-220"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        user.setBirthDath(new BirthDate(dateFormat.parse("15-12-1988").getTime()));

        // signup
        int userId = auth.signup(user);

        // assert
        assertEquals(userId, user.getId());
        assertNotNull(userId);
        assertTrue(userId > 0);
    }

    @Test(expected=DuplicateEmailException.class)
    public void testCantSingupTwoUsersWithSameEmail() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        // 1st user
        User user1 = new User();
        user1.setName("John Lennon");
        user1.setEmail(new Email("john@beatles.com"));
        user1.setGender(Gender.MALE);
        user1.setPassword(new Password("123"));
        user1.setBirthDath(new BirthDate(dateFormat.parse("09-10-2014").getTime()));

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        user2.setBirthDath(new BirthDate(dateFormat.parse("09-10-2014").getTime()));

        // Signup users - should throw an exception
        auth.signup(user1);
        auth.signup(user2);
    }

    @Test
    public void testLoginWithBadCredentials() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Email email = new Email("john@beatles.com");
        Password goodPassword = new Password("123");
        Password badPassword = new Password("456");

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(goodPassword);
        user.setBirthDath(new BirthDate(dateFormat.parse("09-10-2014").getTime()));
        auth.signup(user);

        // Try to authenticate
        User.Credentials credentials = new User.Credentials(email, badPassword);
        User authenticatedUser = auth.authenticate(credentials);
        assertNull(authenticatedUser);
    }

    @Test
    public void testLoginWithGoodCredentials() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate(dateFormat.parse("09-10-2014").getTime()));
        auth.signup(user);

        // Try to authenticate
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials);
        assertNotNull(authenticatedUser);
        assertEquals(user.getId(), authenticatedUser.getId());
        assertEquals(email, authenticatedUser.getEmail());
    }

    @Test
    public void testPasswordIsBeingHashedAfterSignup() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Email email = new Email("john@beatles.com");
        Password password = new Password("AbCd1234#!$&");

        // compare hash and original passwords
        assertNotEquals(password.getOriginalPassword(), password.getHashedPassword());

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate(dateFormat.parse("09-10-2014").getTime()));
        auth.signup(user);

        // authenticate user
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials);
        assertNotNull(authenticatedUser);

        // compare passwords
        assertEquals(password, authenticatedUser.getPassword());
    }

}
