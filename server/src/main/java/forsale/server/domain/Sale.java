package forsale.server.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Sale {

    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private int id;

    private String title;

    private String extra;

    private Vendor vendor;

    private Date startDate;

    private Date endDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public Vendor getVendor() {
        return vendor;
    }

    public void setVendor(Vendor vendor) {
        this.vendor = vendor;
    }

    public Date getStartDate() { return this.startDate; }

    public void setStartDate(Date startDate) { this.startDate = startDate; }

    public Date getEndDate() { return this.endDate; }

    public void setEndDate(Date endDate) { this.endDate = endDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Sale sale1 = (Sale)o;

        return sale1.id == this.id && sale1.title.equals(this.title) && sale1.extra.equals(this.extra) &&
                dateFormat.format(sale1.startDate).equals(dateFormat.format(this.startDate)) &&
                dateFormat.format(sale1.endDate).equals(dateFormat.format(this.endDate)) &&
                sale1.vendor.equals(this.vendor);
    }

}
