package forsale.server.service.event;

import forsale.server.domain.Sale;

public class SaleViewEvent {

    final private Sale sale;

    public SaleViewEvent(Sale sale) {
        this.sale = sale;
    }

    public Sale getSale() {
        return sale;
    }

}
