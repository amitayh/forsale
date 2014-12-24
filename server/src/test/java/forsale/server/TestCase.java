package forsale.server;

import forsale.server.ioc.Container;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.Statement;

abstract public class TestCase {

    public static final String TEST_DB_MYSQL = "forsale_test";

    public static final int TEST_DB_REDIS = 1;

    protected static final Container container = Bootstrap.createIocContainer();

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
        stmt.execute("SET FOREIGN_KEY_CHECKS = 0");
        stmt.execute("TRUNCATE TABLE categories");
        stmt.execute("TRUNCATE TABLE sales");
        stmt.execute("TRUNCATE TABLE user_favorite_categories");
        stmt.execute("TRUNCATE TABLE user_favorite_vendors");
        stmt.execute("TRUNCATE TABLE users");
        stmt.execute("TRUNCATE TABLE vendor_categories");
        stmt.execute("TRUNCATE TABLE vendors");
        stmt.execute("SET FOREIGN_KEY_CHECKS = 1");
    }

    protected void flushRedis() throws Exception {
        Jedis redis = getRedis();
        redis.flushAll();
    }

}
