package forsale.server.service.listener;

import forsale.server.events.Listener;
import forsale.server.service.SalesService;
import forsale.server.service.event.SaleViewEvent;

public class SaleViewListener implements Listener<SaleViewEvent> {

    final private SalesService sales;

    public SaleViewListener(SalesService sales) {
        this.sales = sales;
    }

    @Override
    public void dispatch(SaleViewEvent event) {
        sales.increaseViewCount(event.getSale());
    }

}
