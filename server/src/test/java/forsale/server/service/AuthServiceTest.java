package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.Password;
import forsale.server.domain.User;
import forsale.server.service.exception.DuplicateEmailException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.*;

public class AuthServiceTest extends TestCase {

    private AuthService auth;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection) container.get("mysql")); // clear db rows

        auth = (AuthService) container.get("service.auth");
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
        Calendar calendar = new GregorianCalendar(1988, 12, 15);
        user.setBirthDath(calendar.getTime());

        // signup
        int userId = auth.signup(user);

        // assert
        assertEquals(userId, user.getId());
        assertNotNull(userId);
        assertTrue(userId > 0);
    }

    @Test(expected=DuplicateEmailException.class)
    public void testCantSingupTwoUsersWithSameEmail() throws Exception {
        // 1st user
        User user1 = new User();
        user1.setName("John Lennon");
        user1.setEmail(new Email("john@beatles.com"));
        user1.setGender(Gender.MALE);
        user1.setPassword(new Password("123"));
        Calendar calendar1 = new GregorianCalendar(1940, 10, 9);
        user1.setBirthDath(calendar1.getTime());

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        Calendar calendar2 = new GregorianCalendar(1940, 10, 9);
        user2.setBirthDath(calendar2.getTime());

        // Signup users - should throw an exception
        auth.signup(user1);
        auth.signup(user2);
    }

    @Test
    public void testLoginWithBadCredentials() throws Exception {
        Email email = new Email("john@beatles.com");
        Password goodPassword = new Password("123");
        Password badPassword = new Password("456");

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(goodPassword);
        Calendar calendar1 = new GregorianCalendar(1940, 10, 9);
        user.setBirthDath(calendar1.getTime());
        auth.signup(user);

        // Try to authenticate
        User.Credentials credentials = new User.Credentials(email, badPassword);
        User authenticatedUser = auth.authenticate(credentials);
        assertNull(authenticatedUser);
    }

    @Test
    public void testLoginWithGoodCredentials() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        Calendar calendar1 = new GregorianCalendar(1940, 10, 9);
        user.setBirthDath(calendar1.getTime());
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
        Calendar calendar1 = new GregorianCalendar(1940, 10, 9);
        user.setBirthDath(calendar1.getTime());
        auth.signup(user);

        // authenticate user
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials);
        assertNotNull(authenticatedUser);

        // compare passwords
        assertEquals(password, authenticatedUser.getPassword());
    }

}
