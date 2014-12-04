package forsale.server.service;

import forsale.server.domain.User;

public class AuthService implements AuthServiceInterface {

    final private UsersServiceInterface usersService;

    public AuthService(UsersServiceInterface usersService) {
        this.usersService = usersService;
    }

    @Override
    public int signup(User user) throws Exception {
        return this.usersService.insert(user);
    }

    @Override
    public User authenticate(User.Credentials credentials) throws Exception {
        return this.usersService.get(credentials);
    }
}
