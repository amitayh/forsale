package forsale.server.service.provider;

import forsale.server.ioc.Container;
import forsale.server.ioc.ServiceProvider;

import java.sql.DriverManager;

public class MysqlServiceProvider implements ServiceProvider {

    public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver";

    public static final String DEFAULT_HOST = "localhost";

    public static final String DEFAULT_PORT = "3306";

    @Override
    public Object create(Container container) throws Exception {
        Class.forName(DRIVER_CLASS);
        String url = getUrl(container);
        String user = (String) container.get("mysql.user");
        String password = (String) container.get("mysql.password");

        return DriverManager.getConnection(url, user, password);
    }

    private String getUrl(Container container) throws Exception {
        String host = (String) container.get("mysql.host", DEFAULT_HOST);
        String port = (String) container.get("mysql.port", DEFAULT_PORT);
        String db = (String) container.get("mysql.db");

        return "jdbc:mysql://" + host + ":" + port + "/" + db;
    }

}
