package forsale.server.service;

import forsale.server.domain.User;

public interface UsersServiceInterface {

    public User get(int userId) throws Exception;

    public void edit(User user) throws Exception;

    public int insert(User user) throws Exception;

}
