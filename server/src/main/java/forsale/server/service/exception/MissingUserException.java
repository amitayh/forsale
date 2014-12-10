package forsale.server.service.exception;

public class MissingUserException extends Exception {

    public MissingUserException(int userId) {
        super("User with ID " + userId + " was not found");
    }

}
