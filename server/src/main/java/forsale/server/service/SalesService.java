package forsale.server.service;

import redis.clients.jedis.Jedis;

import java.sql.Connection;

public class SalesService implements SalesServiceInterface {

    private Connection mysql;

    private Jedis redis;

    public SalesService(Connection mysql, Jedis redis) {
        this.mysql = mysql;
        this.redis = redis;
    }

}
