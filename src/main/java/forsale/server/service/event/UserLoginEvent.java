package forsale.server.service.event;

import forsale.server.domain.User;

public class UserLoginEvent {

    final private User user;

    public UserLoginEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
