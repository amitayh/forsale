package forsale.server.service.exception;

import forsale.server.domain.Email;

public class DuplicateEmailException extends Exception {

    public DuplicateEmailException(Email email, Throwable cause) {
        super("Unable to insert user - email '" + email.toString() + "' already exists", cause);
    }

}
