package forsale.server.service;

import java.net.URL;
import java.sql.Connection;
import java.util.Scanner;

public class DbBuilderService {

    public static final String SCHEMA_RESOURCE = "db/schema.sql";

    public static final String SQL_DELIMITER = ";";

    final private Connection mysql;

    public DbBuilderService(Connection mysql) {
        this.mysql = mysql;
    }

    public void createTables() throws Exception {
        Scanner scanner = getScanner(SCHEMA_RESOURCE);
        Transactor transactor = new Transactor(mysql);
        while (scanner.hasNext()) {
            String query = scanner.next().trim();
            transactor.add(query);
        }
        transactor.transact();
    }

    private Scanner getScanner(String name) throws Exception {
        URL schema = getResource(name);
        Scanner scanner = new Scanner(schema.openStream());
        scanner.useDelimiter(SQL_DELIMITER);
        return scanner;
    }

    private URL getResource(String name) throws Exception {
        URL resource = getClass().getClassLoader().getResource(name);
        if (resource == null) {
            throw new Exception("Unable to locate resource '" + name + "'");
        }
        return resource;
    }

}
