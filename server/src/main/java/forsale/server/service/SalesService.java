package forsale.server.service;

import forsale.server.domain.Sale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class SalesService implements SalesServiceInterface {

    private Connection mysql;

    public SalesService(Connection mysql) {
        this.mysql = mysql;
    }

    @Override
    public int insert(Sale sale) throws Exception {
        String sql = "INSERT INTO sales (sale_title, sale_extra, vendor_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, sale.getTitle());
        stmt.setString(2, sale.getExtra());
        stmt.setInt(3, sale.getVendor().getId());
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            sale.setId(rs.getInt(1));
        }

        return sale.getId();
    }

    @Override
    public List<Sale> getPopular() {
        return null;
    }

}
