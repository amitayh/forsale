package forsale.server.domain;

import forsale.server.domain.exception.ValidationException;
import org.apache.commons.validator.routines.EmailValidator;

final public class Email {

    final private static EmailValidator validator = EmailValidator.getInstance();

    final private String email;

    public Email(String email) throws ValidationException {
        this.email = getEmail(email);
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

    private String getEmail(String email) throws ValidationException {
        if (!validator.isValid(email)) {
            throw new ValidationException("Invalid email address '" + email + "'");
        }
        return email.toLowerCase();
    }

}
