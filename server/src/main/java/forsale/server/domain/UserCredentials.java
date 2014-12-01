package forsale.server.domain;

public class UserCredentials {

    final private Email email;

    final private String password;

    public UserCredentials(Email email, String password) {
        this.email = email;
        this.password = password;
    }

    public Email getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

}
