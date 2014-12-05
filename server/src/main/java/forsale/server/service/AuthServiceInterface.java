package forsale.server.service;

import forsale.server.domain.User;

public interface AuthServiceInterface {

    public int signup(User user, String sessionId) throws Exception;

    public User authenticate(User.Credentials credentials, String sessionId) throws  Exception;

    public int getUserId(String sessionId);

    public boolean isSessionExpired(String sessionId);

}
