package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import forsale.server.service.exception.DuplicateEmailException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AuthServiceTest extends TestCase {

    private AuthServiceInterface auth;

    @Before
    public void setUp() throws Exception {
        flushMysql(); // clear db rows

        UsersServiceInterface users = (UsersService) container.get("service.users");
        auth = new AuthService(getMysql(), users);
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
        user.setBirthDath(new BirthDate("15-12-1988"));

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
        user1.setBirthDath(new BirthDate("09-10-2014"));

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        user2.setBirthDath(new BirthDate("09-10-2014"));

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
        user.setBirthDath(new BirthDate("09-10-2014"));
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
        user.setBirthDath(new BirthDate("09-10-2014"));
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
        assertNotEquals("AbCd1234#!$&", password.getHashedPassword());

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("09-10-2014"));
        auth.signup(user);

        // authenticate user
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials);
        assertNotNull(authenticatedUser);

        // compare passwords
        assertEquals(password, authenticatedUser.getPassword());
    }

}
