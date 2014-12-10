package forsale.server.service;

import forsale.server.domain.User;
import forsale.server.service.exception.SessionExpiredException;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

public class AuthService {

    public static final String USER_ID_ATTR = "user_id";

    private static final int EXPIRE_TIME = (int) TimeUnit.HOURS.toSeconds(1);

    final private Jedis redis;

    final private UsersService users;

    public AuthService(UsersService users, Jedis redis) {
        this.users = users;
        this.redis = redis;
    }

    public boolean login(User user, HttpSession session) {
        session.setAttribute(USER_ID_ATTR, user.getId());
        setRedisHash(user);
        return true;
    }

    public boolean logout(HttpSession session) throws Exception {
        Integer userId = getUserId(session);
        removeRedisHash(userId);
        session.invalidate();
        return true;
    }

    public User getUser(HttpSession session) throws Exception {
        Integer userId = getUserId(session);
        return users.get(userId);
    }

    private void setRedisHash(User user) {
        String redisKey = getRedisKey(user.getId());
        redis.hset(redisKey, "email", user.getEmail().toString());
        redis.hset(redisKey, "name", user.getName());
        redis.expire(redisKey, EXPIRE_TIME);
    }

    private void removeRedisHash(Integer userId) {
        redis.del(getRedisKey(userId));
    }

    private String getRedisKey(Integer userId) {
        return "user:" + userId.toString();
    }

    private Integer getUserId(HttpSession session) throws Exception {
        Integer userId = (Integer) session.getAttribute(USER_ID_ATTR);
        if (userId == null) {
            throw new SessionExpiredException();
        }
        return userId;
    }

}
