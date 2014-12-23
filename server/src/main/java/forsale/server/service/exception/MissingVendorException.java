package forsale.server.service.exception;

import java.util.Set;

public class MissingVendorException extends Exception {

    public MissingVendorException(int vendorId) {
        super("Vendor with ID " + vendorId + " was not found");
    }

    public MissingVendorException(Set<Integer> missingVendorIds) {
        super("Vendors with IDs " + missingVendorIds + " were not found");
    }

}
