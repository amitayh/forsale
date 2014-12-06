package forsale.server.service;

import forsale.server.domain.Vendor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class VendorsService implements VendorsServiceInterface {

    final private Connection mysql;

    public VendorsService(Connection mysql) {
        this.mysql = mysql;
    }

    @Override
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

    @Override
    public Vendor get(int vendorId) throws Exception {
        Vendor vendor = null;

        String sql = "SELECT * FROM vendors WHERE vendor_id = ?";
        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, vendorId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            vendor = new Vendor();
            vendor.setId(rs.getInt("vendor_id"));
            vendor.setName(rs.getString("vendor_name"));
        }

        return vendor;
    }

}
