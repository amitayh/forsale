package forsale.server.service;

import forsale.server.domain.Sale;

import java.util.List;
import java.util.Set;

public interface SalesServiceInterface {

    public int insert(Sale sale) throws Exception;

    public Sale get(int saleId) throws Exception;

    public List<Sale> getSalesByIds(Set<Integer> ids) throws Exception;

    public List<Sale> getRecent() throws Exception;

    public List<Sale> getPopular() throws Exception;

    public double increaseViewCount(Sale sale);

}
