package forsale.server;

import forsale.server.dependencyinjection.Container;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.Statement;

abstract public class TestCase {

    public static final String TEST_DB_MYSQL = "forsale_test";

    public static final int TEST_DB_REDIS = 1;

    protected static final Container container = Bootstrap.createDependencyInjectionContainer();

    static {
        initContainer();
    }

    private static void initContainer() {
        container.set("mysql.db", TEST_DB_MYSQL);
        container.set("redis.db", TEST_DB_REDIS);
    }

    protected Connection getMysql() throws Exception {
        return (Connection) container.get("mysql");
    }

    protected Jedis getRedis() throws Exception {
        return (Jedis) container.get("redis");
    }

    protected void flushMysql() throws Exception {
        Connection mysql = getMysql();
        Statement stmt = mysql.createStatement();
        stmt.execute("DELETE FROM user_favorite_vendors;");
        stmt.execute("DELETE FROM sales;");
        stmt.execute("DELETE FROM vendors;");
        stmt.execute("DELETE FROM users;");
    }

    protected void flushRedis() throws Exception {
        Jedis redis = getRedis();
        redis.flushAll();
    }

}
