package forsale.server.service;

import forsale.server.domain.Sale;

import java.util.List;

public interface SalesServiceInterface {

    public int insert(Sale sale) throws Exception;

    public List<Sale> getPopular();

}
