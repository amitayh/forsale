package forsale.server.service;

import forsale.server.domain.User;
import forsale.server.domain.UserCredentials;

import java.sql.Connection;

public class AuthService implements AuthServiceInterface {

    final private Connection mysql;

    public AuthService(Connection mysql) {
        this.mysql = mysql;
    }

    @Override
    public int register(User user) throws Exception {
        // TODO
        return 0;
    }

    @Override
    public User authenticate(UserCredentials credentials) throws Exception {
        // TODO
        return null;
    }

}
