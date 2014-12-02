package forsale.server.service.exception;

import forsale.server.domain.Email;

public class DuplicateEmailException extends Exception {

    final private Email email;

    public DuplicateEmailException(Email email, String message, Throwable cause) {
        super(message, cause);
        this.email = email;
    }

    public Email getEmail() {
        return email;
    }

}
