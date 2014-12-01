package forsale.server.service;

import forsale.server.domain.User;

public interface AuthServiceInterface {

    public int register(User user) throws Exception;

    public User authenticate(User.Credentials credentials) throws  Exception;

}
