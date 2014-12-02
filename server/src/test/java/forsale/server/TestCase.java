package forsale.server;

import forsale.server.dependencyinjection.Container;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

abstract public class TestCase {

    public static final String TEST_DB_MYSQL = "forsale_test";

    public static final int TEST_DB_REDIS = 1;

    protected Container getTestContainer() throws Exception {
        Container container = Bootstrap.createDependencyInjectionContainer();
        container.set("mysql.db", TEST_DB_MYSQL);
        container.set("redis.db", TEST_DB_REDIS);

        return container;
    }

    protected void flush(Connection conn) throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.execute("DELETE FROM sales;");
        stmt.execute("DELETE FROM vendors;");
    }

    protected void flush(Jedis redis) {
        redis.flushAll();
    }

}
