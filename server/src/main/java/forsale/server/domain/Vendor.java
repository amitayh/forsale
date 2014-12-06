package forsale.server.domain;

public class Vendor {

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Vendor ven1 = (Vendor)o;

        return ven1.name.equals(this.name) && ven1.id == this.id;
    }
}
