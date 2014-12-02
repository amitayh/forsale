package forsale.server.domain;

public class Email {

    final private String email;

    public Email(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return this.email;
    }

}
