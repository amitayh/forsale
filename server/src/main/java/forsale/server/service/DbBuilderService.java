package forsale.server.service;

import java.net.URL;
import java.sql.Connection;

public class DbBuilderService {

    public static final String SCHEMA_RESOURCE = "db/schema.sql";

    final private Connection mysql;

    public DbBuilderService(Connection mysql) {
        this.mysql = mysql;
    }

    public void createTables() throws Exception {
        URL schema = Utils.getResource(SCHEMA_RESOURCE);
        QueryScanner queries = new QueryScanner(schema);
        Transactor transactor = new Transactor(mysql);
        for (String query : queries) {
            transactor.add(query);
        }
        transactor.transact();
    }

}
