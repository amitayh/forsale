package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.dependencyinjection.Container;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

import static org.junit.Assert.assertEquals;

public class SessionsServiceTest extends TestCase {

    private SessionsServiceInterface sessionsService;

    @Before
    public void setUp() throws Exception {
        Container container = getTestContainer();
        flush((Connection)container.get("mysql"));
        this.sessionsService = (SessionsServiceInterface)container.get("service.sessions");
    }

    @After
    public void tearDown() {
        this.sessionsService = null;
    }

    @Test
    public void testSessionIdSavedToRedis() {
        int userId = 321;
        String sessionId = "123";

        this.sessionsService.setSessionId(sessionId, userId);
        int sessionsUserId = this.sessionsService.getUserId(sessionId);

        assertEquals(userId, sessionsUserId);
    }
}
