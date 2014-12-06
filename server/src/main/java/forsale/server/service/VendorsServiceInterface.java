package forsale.server.service;

import forsale.server.domain.Vendor;

public interface VendorsServiceInterface {

    public int insert(Vendor vendor) throws Exception;

    public Vendor get(int vendorId) throws Exception;

}
