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
        flushMysql();
        flushRedis();
        UsersServiceInterface users = (UsersServiceInterface) container.get("service.users");
        auth = new AuthService(users, getRedis());
    }

    @After
    public void tearDown() {
        auth = null;
    }

    @Test
    public void testSignupSavesUser() throws Exception {
        String sessionId = "321";

        // prepare new user
        User user = new User();
        user.setName("Assaf Grimberg");
        user.setEmail(new Email("assafgrim@gmail.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("AD#ri493-220"));
        user.setBirthDath(new BirthDate("1988-12-15"));

        // signup
        int userId = auth.signup(user, sessionId);

        // assert
        assertEquals(userId, user.getId());
        assertNotNull(userId);
        assertTrue(userId > 0);
    }

    @Test(expected=DuplicateEmailException.class)
    public void testCantSingupTwoUsersWithSameEmail() throws Exception {
        String sessionId1 = "321";
        String sessionId2 = "456";

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
        auth.signup(user1, sessionId1);
        auth.signup(user2, sessionId2);
    }

    @Test
    public void testLoginWithBadCredentials() throws Exception {
        Email email = new Email("john@beatles.com");
        Password goodPassword = new Password("123");
        Password badPassword = new Password("456");

        String sessionId = "321";

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(goodPassword);
        user.setBirthDath(new BirthDate("2014-10-09"));
        int userId = auth.signup(user, sessionId);

        assertTrue(userId > 0);

        // Try to authenticate
        User.Credentials credentials = new User.Credentials(email, badPassword);
        User authenticatedUser = auth.authenticate(credentials, sessionId);
        assertNull(authenticatedUser);
    }

    @Test
    public void testLoginWithGoodCredentials() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        String sessionId = "321";

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("2014-10-09"));
        int userId = auth.signup(user, sessionId);

        assertTrue(userId >= 0);

        // Try to authenticate
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials, sessionId);
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

        String sessionId = "321";

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("2014-10-09"));
        int userId = auth.signup(user, sessionId);

        assertTrue(userId >= 0);

        // authenticate user
        User.Credentials credentials = new User.Credentials(email, password);
        User authenticatedUser = auth.authenticate(credentials, sessionId);
        assertNotNull(authenticatedUser);

        // compare passwords
        assertEquals(password, authenticatedUser.getPassword());
    }

    @Test(expected=Exception.class)
    public void testTwoDiffUsersCanNotRegitserWithSameSessionId() throws Exception {
        String sessionId = "321";

        // 1st user
        User user1 = new User();
        user1.setName("John Lennon");
        user1.setEmail(new Email("john1@beatles.com"));
        user1.setGender(Gender.MALE);
        user1.setPassword(new Password("123"));
        user1.setBirthDath(new BirthDate("2014-10-09"));

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john2@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        user2.setBirthDath(new BirthDate("2014-10-09"));

        // Signup users with same session id - should throw an exception
        auth.signup(user1, sessionId);
        auth.signup(user2, sessionId);
    }

    @Test(expected=Exception.class)
    public void testTwoDiffUsersCanNotLoginWithSameSessionId() throws Exception {
        String sessionId1 = "321";
        String sessionId2 = "654";

        // 1st user
        User user1 = new User();
        user1.setName("John Lennon");
        user1.setEmail(new Email("john1@beatles.com"));
        user1.setGender(Gender.MALE);
        user1.setPassword(new Password("123"));
        user1.setBirthDath(new BirthDate("2014-10-09"));

        // 2nd user
        User user2 = new User();
        user2.setName("John Lennon #2");
        user2.setEmail(new Email("john2@beatles.com"));
        user2.setGender(Gender.MALE);
        user2.setPassword(new Password("456"));
        user2.setBirthDath(new BirthDate("2014-10-09"));

        // Signup users with different session ids
        user1.setId(auth.signup(user1, sessionId1));
        user2.setId(auth.signup(user2, sessionId2));

        assertTrue(user1.getId() >= 0);
        assertTrue(user2.getId() >= 0);

        // login with same session id - should throw exception
        auth.authenticate(new User.Credentials(user1.getEmail(), user1.getPassword()), sessionId1);
        auth.authenticate(new User.Credentials(user2.getEmail(), user2.getPassword()), sessionId1);
    }

    @Test
    public void testThatSessionIdReturnCorrectUserId() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        String sessionId = "321";

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("2014-10-09"));
        int userId = auth.signup(user, sessionId);

        assertTrue(userId >= 0);

        // check user id for session
        assertEquals(userId, auth.getUserId(sessionId));
    }

    @Test
    public void testThatSessionHistoryClearedWhenLoginAgainWithNewSessionId() throws Exception{
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");

        String sessionId1 = "321";

        // Signup user
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(email);
        user.setGender(Gender.MALE);
        user.setPassword(password);
        user.setBirthDath(new BirthDate("2014-10-09"));
        int userId = auth.signup(user, sessionId1);

        assertTrue(userId >= 0);

        // 1st Login
        auth.authenticate(new User.Credentials(user.getEmail(), user.getPassword()), sessionId1);

        // 2nd Login
        String sessionId2 = "989776";
        auth.authenticate(new User.Credentials(user.getEmail(), user.getPassword()), sessionId2);

        // check that previous session history cleared
        assertTrue(auth.getUserId(sessionId1) < 0);
    }
}
