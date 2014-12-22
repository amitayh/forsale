package forsale.server.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class Transactor {

    final private Connection conn;

    final private List<String> queries = new LinkedList<>();

    public Transactor(Connection conn) {
        this.conn = conn;
    }

    public boolean add(String query) {
        return queries.add(query);
    }

    public boolean transact() throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            Statement stmt = conn.createStatement();
            for (String query : queries) {
                stmt.execute(query);
            }
            conn.commit();
            result = true;
        } catch (SQLException e) {
            conn.rollback();
        } finally {
            conn.setAutoCommit(autoCommit);
        }
        return result;
    }

}
