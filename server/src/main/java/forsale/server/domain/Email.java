package forsale.server.domain;

final public class Email {

    final private String email;

    public Email(String email) {
        this.email = (email != null) ? email.toLowerCase() : null;
    }

    @Override
    public String toString() {
        return this.email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Email email1 = (Email) o;

        return !(email != null ? !email.equals(email1.email) : email1.email != null);
    }

    @Override
    public int hashCode() {
        return email != null ? email.hashCode() : 0;
    }

}
