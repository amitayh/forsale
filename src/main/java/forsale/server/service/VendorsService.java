package forsale.server.service;

import forsale.server.Utils;
import forsale.server.domain.Vendor;
import forsale.server.service.exception.MissingVendorException;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    public List<Vendor> getAll() throws Exception {
        String sql = "SELECT * FROM vendors";
        PreparedStatement stmt = mysql.prepareStatement(sql);

        return getResults(stmt);
    }

    public Vendor get(int vendorId) throws Exception {
        Vendor vendor;

        String sql = "SELECT * FROM vendors WHERE vendor_id = ?";
        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, vendorId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            vendor = hydrate(rs);
        } else {
            throw new MissingVendorException(vendorId);
        }

        return vendor;
    }

    public List<Vendor> get(Set<Integer> vendorIds) throws Exception {
        List<Vendor> result = new ArrayList<>();

        if (!vendorIds.isEmpty()) {
            String sql =
                    "SELECT * " +
                    "FROM vendors " +
                    "WHERE vendor_id IN " + Utils.getMultipleParametersList(vendorIds.size());

            PreparedStatement stmt = mysql.prepareStatement(sql);

            // Bind IDs to query
            int parameterIndex = 1;
            for (Integer vendorId : vendorIds) {
                stmt.setInt(parameterIndex, vendorId);
                parameterIndex++;
            }

            result = getResults(stmt);

            if (result.size() != vendorIds.size()) {
                Set<Integer> missingVendorIds = findMissingVendorIds(result, vendorIds);
                throw new MissingVendorException(missingVendorIds);
            }
        }

        return result;
    }

    public static Vendor hydrate(ResultSet rs) throws SQLException {
        Vendor vendor = new Vendor();
        vendor.setId(rs.getInt(Field.VENDOR_ID));
        vendor.setName(rs.getString(Field.VENDOR_NAME));

        return vendor;
    }

    private List<Vendor> getResults(PreparedStatement stmt) throws SQLException {
        List<Vendor> results = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            results.add(hydrate(rs));
        }

        return results;
    }

    private Set<Integer> findMissingVendorIds(List<Vendor> result, Set<Integer> vendorIds) {
        Set<Integer> foundVendorIds = new HashSet<>();
        Set<Integer> missingVendorIds = new HashSet<>();
        for (Vendor vendor : result) {
            foundVendorIds.add(vendor.getId());
        }
        for (Integer vendorId : vendorIds) {
            if (!foundVendorIds.contains(vendorId)) {
                missingVendorIds.add(vendorId);
            }
        }
        return missingVendorIds;
    }

}
