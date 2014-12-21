package forsale.server.service;

import forsale.server.domain.Vendor;

import java.sql.*;

public class VendorsService {

    public static final class Field {
        public static final String VENDOR_ID = "vendor_id";
        public static final String VENDOR_NAME = "vendor_name";
    }

    final private Connection mysql;

    public VendorsService(Connection mysql) {
        this.mysql = mysql;
    }

    public int insert(Vendor vendor) throws Exception {
        String sql = "INSERT INTO vendors (vendor_name) VALUES (?)";
        PreparedStatement stmt = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, vendor.getName());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            vendor.setId(rs.getInt(1));
        }

        return vendor.getId();
    }

    public Vendor get(int vendorId) throws Exception {
        Vendor vendor = null;

        String sql = "SELECT * FROM vendors WHERE vendor_id = ?";
        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, vendorId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            vendor = hydrate(rs);
        }

        return vendor;
    }

    public static Vendor hydrate(ResultSet rs) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setId(rs.getInt(Field.VENDOR_ID));
        vendor.setName(rs.getString(Field.VENDOR_NAME));

        return vendor;
    }

}
