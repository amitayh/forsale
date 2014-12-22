package forsale.server.service;

import forsale.server.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TransactorTest extends TestCase {

    private Transactor transactor;

    private Statement stmt;

    @Before
    public void setUp() throws Exception {
        flushMysql();

        Connection mysql = getMysql();
        transactor = new Transactor(mysql);
        stmt = mysql.createStatement();
    }

    @After
    public void tearDown() throws Exception {
        stmt.execute("DROP TABLE IF EXISTS transactor_test");

        transactor = null;
        stmt = null;
    }

    @Test
    public void testTransactionFails() throws Exception {
        transactor.add("CREATE TABLE transactor_test (id INT NOT NULL)");
        transactor.add("INSERT INTO transactor_test (name) VALUES ('foo')");
        assertFalse(transactor.transact());

        ResultSet rs = stmt.executeQuery("SELECT * FROM transactor_test");
        assertFalse(rs.next());
    }

    @Test
    public void testTransactionSucceeds() throws Exception {
        transactor.add("CREATE TABLE transactor_test (id INT NOT NULL)");
        transactor.add("INSERT INTO transactor_test (id) VALUES (3)");
        assertTrue(transactor.transact());

        ResultSet rs = stmt.executeQuery("SELECT * FROM transactor_test");
        assertTrue(rs.next());
        assertEquals(3, rs.getInt(1));
    }

}