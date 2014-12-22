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
        URL schema = getResource(SCHEMA_RESOURCE);
        QueryScanner queries = new QueryScanner(schema);
        Transactor transactor = new Transactor(mysql);
        for (String query : queries) {
            transactor.add(query);
        }
        transactor.transact();
    }

    private URL getResource(String name) throws Exception {
        URL resource = getClass().getClassLoader().getResource(name);
        if (resource == null) {
            throw new Exception("Unable to locate resource '" + name + "'");
        }
        return resource;
    }

}
