package forsale.server.service.listener;

import forsale.server.domain.Sale;
import forsale.server.service.SalesService;
import forsale.server.service.event.SaleViewEvent;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SaleViewListenerTest {

    @Test
    public void testIncreaseViewCount() {
        SalesService sales = mock(SalesService.class);
        Sale sale = new Sale();
        SaleViewListener listener = new SaleViewListener(sales);
        listener.dispatch(new SaleViewEvent(sale));
        verify(sales).increaseViewCount(sale);
    }

}