package forsale.server.service;

import forsale.server.domain.Sale;

import java.util.List;
import java.util.Set;

public interface SalesServiceInterface {

    public int insert(Sale sale) throws Exception;

    public List<Sale> getSalesById(Set<Integer> ids) throws Exception;

    public List<Sale> getPopular() throws Exception;

    public double increaseViewCount(Sale sale);

}
