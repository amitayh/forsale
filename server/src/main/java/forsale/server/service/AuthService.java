package forsale.server.service;

import forsale.server.domain.User;
import redis.clients.jedis.Jedis;

public class AuthService implements AuthServiceInterface {

    final private int SESSION_EXPIRE_TIME = 60 * 60 * 24 * 365; // 1 year

    final private Jedis jedis;
    final private UsersServiceInterface usersService;

    public AuthService(UsersServiceInterface usersService, Jedis jedis) {
        this.usersService = usersService;
        this.jedis = jedis;
    }

    @Override
    public int signup(User user, String sessionId) throws Exception {
        int userId = this.usersService.insert(user);
        setSessionId(sessionId, userId);
        return userId;
    }

    @Override
    public User authenticate(User.Credentials credentials, String sessionId) throws Exception {
        User user = this.usersService.get(credentials);
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

    @Override
    public int getUserId(String sessionId) {
        String userIdString = jedis.get("session:" + sessionId);
        if (userIdString == null) {
            return -1;
        }
        return Integer.parseInt(userIdString);
    }

    @Override
    public boolean isSessionExpired(String sessionId) {
        return jedis.exists("session:" + sessionId);
    }

}
