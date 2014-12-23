package forsale.server.db;

import forsale.server.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.*;

public class TransactorTest extends TestCase {

    private Connection mysql;

    private Transactor transactor;

    @Before
    public void setUp() throws Exception {
        flushMysql();

        mysql = getMysql();
        transactor = new Transactor(mysql);
    }

    @After
    public void tearDown() throws Exception {
        mysql.prepareStatement("DROP TABLE IF EXISTS transactor_test").execute();

        mysql = null;
        transactor = null;
    }

    @Test
    public void testTransactionFails() throws Exception {
        transactor.add("CREATE TABLE transactor_test (id INT NOT NULL)");
        transactor.add("INSERT INTO transactor_test (name) VALUES ('foo')");
        assertFalse(transactor.transact());

        PreparedStatement stmt = mysql.prepareStatement("SELECT * FROM transactor_test");
        ResultSet rs = stmt.executeQuery();
        assertFalse(rs.next());
    }

    @Test
    public void testTransactionSucceeds() throws Exception {
        transactor.add("CREATE TABLE transactor_test (id INT NOT NULL)");
        transactor.add("INSERT INTO transactor_test (id) VALUES (3)");
        assertTrue(transactor.transact());

        PreparedStatement stmt = mysql.prepareStatement("SELECT * FROM transactor_test");
        ResultSet rs = stmt.executeQuery();
        assertTrue(rs.next());
        assertEquals(3, rs.getInt(1));
    }

    @Test
    public void testTransactionWithPreparedStatements() throws Exception {
        String sql = "INSERT INTO transactor_test (id, name) VALUES (?, ?)";

        PreparedStatement stmt1 = mysql.prepareStatement(sql);
        stmt1.setInt(1, 1);
        stmt1.setString(2, "foo");

        PreparedStatement stmt2 = mysql.prepareStatement(sql);
        stmt2.setInt(1, 2);
        stmt2.setString(2, "bar");

        transactor.add("CREATE TABLE transactor_test (id INT NOT NULL, name VARCHAR(32) NOT NULL)");
        transactor.add(stmt1);
        transactor.add(stmt2);
        assertTrue(transactor.transact());

        PreparedStatement stmt = mysql.prepareStatement("SELECT * FROM transactor_test");
        ResultSet rs = stmt.executeQuery();

        // First row
        assertTrue(rs.next());
        assertEquals(1, rs.getInt(1));
        assertEquals("foo", rs.getString(2));

        // Second row
        assertTrue(rs.next());
        assertEquals(2, rs.getInt(1));
        assertEquals("bar", rs.getString(2));
    }

}