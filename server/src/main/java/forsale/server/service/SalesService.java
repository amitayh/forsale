package forsale.server.service;

import forsale.server.domain.Sale;
import redis.clients.jedis.Jedis;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SalesService implements SalesServiceInterface {

    public static final String SALE_VIEWS_HASH = "sale_views";

    final private Connection mysql;

    final private Jedis redis;

    public SalesService(Connection mysql, Jedis redis) {
        this.mysql = mysql;
        this.redis = redis;
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
        List<Sale> popular = new ArrayList<>();

        Set<String> popularity = redis.zrange(SALE_VIEWS_HASH, 0, -1);

        return popular;
    }

    @Override
    public double increaseViewCount(Sale sale) {
        return redis.zincrby(SALE_VIEWS_HASH, 1, Integer.toString(sale.getId()));
    }

}
