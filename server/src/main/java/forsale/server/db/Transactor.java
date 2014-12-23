package forsale.server.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class Transactor {

    final private Connection conn;

    final private List<PreparedStatement> statements = new LinkedList<>();

    public Transactor(Connection conn) {
        this.conn = conn;
    }

    public boolean add(PreparedStatement stmt) {
        return statements.add(stmt);
    }

    public boolean add(String query) throws SQLException {
        return statements.add(conn.prepareStatement(query));
    }

    public boolean transact() throws SQLException {
        boolean autoCommit = conn.getAutoCommit();
        boolean result = false;
        try {
            conn.setAutoCommit(false);
            for (PreparedStatement stmt : statements) {
                stmt.execute();
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
