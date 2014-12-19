package forsale.server.service.event;

import forsale.server.domain.User;

public class UserLogoutEvent {

    final private User user;

    public UserLogoutEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

}
