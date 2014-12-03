package forsale.server.service;

import redis.clients.jedis.Jedis;

public class SessionsService implements SessionsServiceInterface {

    private Jedis jedis;

    public SessionsService(Jedis jedis) {
        this.jedis = jedis;
    }

    @Override
    public void setSessionId(String sessionId, Integer userId) {
        jedis.hset("session", sessionId, userId.toString());
    }

    @Override
    public int getUserId(String sessionId) {
        String userIdString = this.jedis.hget("session", sessionId);
        return Integer.parseInt(userIdString);
    }
}
