package forsale.server.service;

import forsale.server.domain.Sale;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.*;

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
    public List<Sale> getSalesById(Set<Integer> ids) throws Exception {
        List<Sale> result = new ArrayList<>();

        if (!ids.isEmpty()) {
            String sql = "SELECT * FROM sales WHERE sale_id IN " + Utils.getMultipleParametersList(ids);
            PreparedStatement stmt = mysql.prepareStatement(sql);

            // Bind IDs to query
            int parameterIndex = 1;
            for (Integer id : ids) {
                stmt.setInt(parameterIndex, id);
                parameterIndex++;
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(hydrate(rs));
            }
        }

        return result;
    }

    @Override
    public List<Sale> getPopular() throws Exception {
        Map<Integer, Sale> salesMap = new HashMap<>();
        Set<Integer> popularSalesIds = getPopularSalesIds();
        for (Sale sale : getSalesById(popularSalesIds)) {
            salesMap.put(sale.getId(), sale);
        }

        List <Sale> popular = new ArrayList<>();
        for (Integer id : popularSalesIds) {
            popular.add(salesMap.get(id));
        }

        return popular;
    }

    @Override
    public double increaseViewCount(Sale sale) {
        return redis.zincrby(SALE_VIEWS_HASH, 1, Integer.toString(sale.getId()));
    }

    private Sale hydrate(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("sale_id"));
        sale.setTitle(rs.getString("sale_title"));

        return sale;
    }

    private Set<Integer> getPopularSalesIds() {
        Set<Integer> ids = new LinkedHashSet<>();
        for (String id : redis.zrevrange(SALE_VIEWS_HASH, 0, -1)) {
            ids.add(Integer.valueOf(id));
        }

        return ids;
    }
}
