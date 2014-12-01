package forsale.server.service;

import forsale.server.Bootstrap;
import forsale.server.dependencyinjection.Container;
import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.User;
import forsale.server.service.provider.MysqlServiceProvider;
import java.sql.Connection;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AuthServiceTest {

    public static final String TEST_DB_MYSQL = "forsale_test";

    private Container container;
    private Logger logger;
    private AuthService auth;

    @Before
    public void setUp() throws Exception {
        this.container = getTestContainer();
        this.logger = (Logger)this.container.get("logger");
        // create auth service object
        MysqlServiceProvider provider = (MysqlServiceProvider)this.container.get("mysql");
        Connection mysql = (Connection)provider.create(this.container);
        this.auth = new AuthService(mysql);

    }

    @After
    public void tearDown() {
        this.container = null;
        this.logger = null;
        this.auth = null;
    }

    @Test
    public void testRegisterAndLogin() throws Exception {
        // prepare new user
        User user = new User();
        user.setName("Assaf Grimberg");
        user.setEmail(new Email("assafgrim@gmail.com"));
        user.setGender(Gender.MALE);
        user.setPassword("AD#ri493-220");
        user.setBirthDath(null);
        user.setId(-1);

        // register user
        logger.info("Registering...");
        testRegister(user);

        // try register again
        logger.info("Registering again...");
        testRegister(user);

        // login with registered user
        logger.info("Login...");
        testLogin(user.getCredentials());
    }

    private void testRegister(User user) throws Exception {
        int userId = auth.signup(user);
        if (userId < 0) {
            logger.severe("Failed to signup user");
        } else {
            logger.fine("User signed up with id: " + userId);
        }
    }

    private void testLogin(User.Credentials credentials) throws Exception {
        User user = auth.authenticate(credentials);
        if (user == null) {
            logger.severe("Failed to login user");
        } else {
            logger.fine("User logged in with id: " + user.getId());
        }
    }

    private Container getTestContainer() throws Exception {
        Container container = Bootstrap.createDependencyInjectionContainer();
        container.set("mysql.db", TEST_DB_MYSQL); // override original db
        return container;
    }
}
