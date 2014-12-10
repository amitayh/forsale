package forsale.server.service.exception;

public class SessionExpiredException extends Exception {

    public SessionExpiredException() {
        super("Session expired");
    }

}
