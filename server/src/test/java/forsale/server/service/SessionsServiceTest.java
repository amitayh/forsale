package forsale.server.service;

import forsale.server.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionsServiceTest extends TestCase {

    private SessionsService sessions;

    @Before
    public void setUp() throws Exception {
        flushMysql();
        sessions = new SessionsService(getRedis());
    }

    @After
    public void tearDown() {
        sessions = null;
    }

    @Test
    public void testSessionIdSavedToRedis() {
        int userId = 321;
        String sessionId = "123";

        sessions.setSessionId(sessionId, userId);
        int sessionsUserId = sessions.getUserId(sessionId);

        assertEquals(userId, sessionsUserId);
    }
}
