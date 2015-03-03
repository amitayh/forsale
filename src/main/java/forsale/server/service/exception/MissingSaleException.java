package forsale.server.service.exception;

import java.util.Set;

public class MissingSaleException extends Exception {

    public MissingSaleException(int saleId) {
        super("Sale with ID " + saleId + " was not found");
    }

    public MissingSaleException(Set<Integer> missingSaleIds) {
        super("Sales with IDs " + missingSaleIds + " were not found");
    }

}
