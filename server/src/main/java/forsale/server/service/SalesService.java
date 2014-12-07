package forsale.server.service;

import forsale.server.domain.Sale;
import forsale.server.domain.User;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.*;

public class SalesService {

    public static final String SALE_VIEWS_HASH = "sale_views";

    public static final int POPULAR_LIMIT = 100;

    final private Connection mysql;

    final private Jedis redis;

    public SalesService(Connection mysql, Jedis redis) {
        this.mysql = mysql;
        this.redis = redis;
    }

    public int insert(Sale sale) throws Exception {
        String sql =
                "INSERT INTO sales " +
                "(sale_title, sale_extra, vendor_id, sale_start, sale_end) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        stmt.setString(1, sale.getTitle());
        stmt.setString(2, sale.getExtra());
        stmt.setInt(3, sale.getVendor().getId());
        stmt.setDate(4, new java.sql.Date(sale.getStartDate().getTime()));
        stmt.setDate(5, new java.sql.Date(sale.getEndDate().getTime()));
        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            sale.setId(rs.getInt(1));
        }

        return sale.getId();
    }

    public Sale get(int saleId) throws Exception {
        Sale sale = null;

        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "WHERE s.sale_id = ?";

        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, saleId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            sale = hydrate(rs);
        }

        return sale;
    }

    public List<Sale> getSalesByIds(Set<Integer> ids) throws Exception {
        List<Sale> result = new ArrayList<>();

        if (!ids.isEmpty()) {
            String sql =
                    "SELECT s.*, v.* " +
                    "FROM sales AS s " +
                    "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                    "WHERE s.sale_id IN " + Utils.getMultipleParametersList(ids.size());

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

    public List<Sale> getRecent() throws Exception {
        List<Sale> result = new ArrayList<>();

        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "ORDER BY s.sale_start DESC";

        PreparedStatement stmt = mysql.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(hydrate(rs));
        }

        return result;
    }

    public List<Sale> getPopular() throws Exception {
        Map<Integer, Sale> salesMap = new HashMap<>();
        Set<Integer> popularSalesIds = getPopularSalesIds(POPULAR_LIMIT);
        for (Sale sale : getSalesByIds(popularSalesIds)) {
            salesMap.put(sale.getId(), sale);
        }

        List <Sale> popular = new ArrayList<>();
        for (Integer id : popularSalesIds) {
            popular.add(salesMap.get(id));
        }

        return popular;
    }

    public List<Sale> getFavorites(User user) throws Exception {
        List<Sale> result = new ArrayList<>();

        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "JOIN user_favorite_vendors AS ufv ON ufv.vendor_id = v.vendor_id " +
                "WHERE ufv.user_id = ?";

        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, user.getId());

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            result.add(hydrate(rs));
        }

        return result;
    }

    public double increaseViewCount(Sale sale) {
        return redis.zincrby(SALE_VIEWS_HASH, 1, Integer.toString(sale.getId()));
    }

    public static Sale hydrate(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt("sale_id"));
        sale.setTitle(rs.getString("sale_title"));
        sale.setExtra(rs.getString("sale_extra"));
        sale.setStartDate(rs.getDate("sale_start"));
        sale.setEndDate(rs.getDate("sale_end"));
        sale.setVendor(VendorsService.hydrate(rs));

        return sale;
    }

    private Set<Integer> getPopularSalesIds(int limit) {
        Set<Integer> ids = new LinkedHashSet<>();
        int offset = 0;
        for (String id : redis.zrevrange(SALE_VIEWS_HASH, offset, limit)) {
            ids.add(Integer.valueOf(id));
        }

        return ids;
    }

}
