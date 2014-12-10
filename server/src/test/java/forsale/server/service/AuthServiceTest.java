package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import forsale.server.service.exception.SessionExpiredException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class AuthServiceTest extends TestCase {

    private UsersService users;

    private Jedis redis;

    private AuthService auth;

    @Before
    public void setUp() throws Exception {
        flushMysql();
        flushRedis();

        users = (UsersService) container.get("service.users");
        redis = getRedis();
        auth = new AuthService(users, redis);
    }

    @After
    public void tearDown() {
        users = null;
        redis = null;
        auth = null;
    }

    @Test
    public void testLoginSavesUserIdToSession() throws Exception {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));

        HttpSession session = createSessionMock();
        auth.login(user, session);

        verify(session).setAttribute("user_id", userId);
    }

    @Test
    public void testLoginSavesUserHashToRedis() throws Exception {
        Integer userId = 1;
        String email = "john@beatles.com";
        String name = "John Lennon";

        User user = new User();
        user.setId(userId);
        user.setName(name);
        user.setEmail(new Email(email));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));

        HttpSession session = createSessionMock();
        auth.login(user, session);

        Map<String, String> userHash = redis.hgetAll("user:" + userId.toString());
        assertEquals(email, userHash.get("email"));
        assertEquals(name, userHash.get("name"));
    }

    @Test
    public void testLogoutInvalidatesSession() throws Exception {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));

        HttpSession session = createSessionMock();
        when(session.getAttribute("user_id")).thenReturn(userId);

        auth.login(user, session);
        auth.logout(session);

        verify(session).invalidate();
    }

    @Test
    public void testLogoutRemovesUserHashFromRedis() throws Exception {
        Integer userId = 1;

        User user = new User();
        user.setId(userId);
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));

        HttpSession session = createSessionMock();
        when(session.getAttribute("user_id")).thenReturn(userId);

        auth.login(user, session);
        auth.logout(session);

        Map<String, String> userHash = redis.hgetAll("user:" + userId.toString());
        assertEquals(0, userHash.size());
    }

    @Test
    public void testGetUserWhenLoggedIn() throws Exception {
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));
        users.insert(user);

        HttpSession session = createSessionMock();
        when(session.getAttribute("user_id")).thenReturn(user.getId());

        auth.login(user, session);
        User authenticatedUser = auth.getUser(session);

        assertEquals(user, authenticatedUser);
    }

    @Test(expected = SessionExpiredException.class)
    public void testGetUserWhenLoggedOut() throws Exception {
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("2014-10-09"));
        users.insert(user);

        HttpSession session = createSessionMock();

        auth.getUser(session);
    }

    private HttpSession createSessionMock() {
        return mock(HttpSession.class);
    }

}
