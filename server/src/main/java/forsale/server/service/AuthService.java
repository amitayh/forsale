package forsale.server.service;

import forsale.server.domain.User;
import forsale.server.service.exception.SessionExpiredException;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;

public class AuthService {

    final static private int SESSION_EXPIRE_TIME = 60 * 60 * 24 * 365; // 1 year

    final private Jedis jedis;

    final private UsersService users;

    public AuthService(UsersService users, Jedis jedis) {
        this.users = users;
        this.jedis = jedis;
    }

    public int signup(User user, String sessionId) throws Exception {
        int userId = users.insert(user);
        setSessionId(sessionId, userId);
        return userId;
    }

    public User authenticate(User.Credentials credentials, String sessionId) throws Exception {
        User user = users.get(credentials);
        if (user != null) {
            setSessionId(sessionId, user.getId());
        }
        return user;
    }

    private void setSessionId(String sessionId, Integer userId) throws Exception {
        int existingUserIdInSession = getUserId(sessionId);
        if (existingUserIdInSession != -1 && existingUserIdInSession != userId) {
            throw new Exception("Session id already in use.");
        }

        clearSessionHistory(userId);
        jedis.set("session:" + sessionId, userId.toString());
        jedis.expire("session:" + sessionId, SESSION_EXPIRE_TIME);
        jedis.set("session:userid:" + userId.toString(), sessionId);
        jedis.expire("session:userid:" + userId.toString(), SESSION_EXPIRE_TIME);
    }

    private void clearSessionHistory(Integer userId) {
        String sessionId = jedis.get("session:userid:" + userId.toString());
        if (sessionId != null) {
            jedis.del("session:userid:" + userId.toString());
            jedis.del("session:" + sessionId);
        }
    }

    public int getUserId(String sessionId) {
        String userIdString = jedis.get("session:" + sessionId);
        if (userIdString == null) {
            return -1;
        }
        return Integer.parseInt(userIdString);
    }

    public boolean isSessionExpired(String sessionId) {
        return !jedis.exists("session:" + sessionId);
    }

    public User getUser(HttpSession session) throws Exception {
        String sessionId = session.getId();
        if (isSessionExpired(sessionId)) {
            session.invalidate();
            throw new SessionExpiredException("Session expired");
        }
        int userId = getUserId(sessionId);
        return users.get(userId);
    }

}
