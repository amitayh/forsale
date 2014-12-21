package forsale.server.service.exception;

public class MissingVendorException extends Exception {

    public MissingVendorException(int vendorId) {
        super("Vendor with ID " + vendorId + " was not found");
    }

}
