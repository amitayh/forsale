package forsale.server.service.exception;

public class MissingSaleException extends Exception {

    public MissingSaleException(int saleId) {
        super("Sale with ID " + saleId + " was not found");
    }

}
