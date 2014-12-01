package forsale.server.service;

import forsale.server.domain.User;
import forsale.server.domain.UserCredentials;

public interface AuthServiceInterface {

    public int register(User user) throws Exception;

    public User authenticate(UserCredentials credentials) throws  Exception;

}
