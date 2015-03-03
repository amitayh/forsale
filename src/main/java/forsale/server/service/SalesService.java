package forsale.server.service;

import forsale.server.Utils;
import forsale.server.domain.Sale;
import forsale.server.domain.SearchCriteria;
import forsale.server.domain.User;
import forsale.server.service.exception.MissingSaleException;
import redis.clients.jedis.Jedis;

import java.sql.*;
import java.util.*;

public class SalesService {

    public static final class Field {
        public static final String SALE_ID = "sale_id";
        public static final String SALE_TITLE = "sale_title";
        public static final String SALE_EXTRA = "sale_extra";
        public static final String SALE_START = "sale_start";
        public static final String SALE_END = "sale_end";
    }

    public static final String SALE_VIEWS_HASH = "sale_views";

    public static final String WILDCARD = "%";

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
        Sale sale;

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
        } else {
            throw new MissingSaleException(saleId);
        }

        return sale;
    }

    public List<Sale> get(Set<Integer> saleIds) throws Exception {
        List<Sale> result = new ArrayList<>();

        if (!saleIds.isEmpty()) {
            String sql =
                    "SELECT s.*, v.* " +
                    "FROM sales AS s " +
                    "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                    "WHERE s.sale_id IN " + Utils.getMultipleParametersList(saleIds.size());

            PreparedStatement stmt = mysql.prepareStatement(sql);

            // Bind IDs to query
            int parameterIndex = 1;
            for (Integer saleId : saleIds) {
                stmt.setInt(parameterIndex, saleId);
                parameterIndex++;
            }

            result = getResults(stmt);

            if (result.size() != saleIds.size()) {
                Set<Integer> missingSaleIds = findMissingSaleIds(result, saleIds);
                throw new MissingSaleException(missingSaleIds);
            }
        }

        return result;
    }

    public List<Sale> getRecent() throws Exception {
        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "ORDER BY s.sale_start DESC";

        PreparedStatement stmt = mysql.prepareStatement(sql);

        return getResults(stmt);
    }

    public List<Sale> getPopular() throws Exception {
        Map<Integer, Sale> salesMap = new HashMap<>();
        Set<Integer> popularSalesIds = getPopularSalesIds(POPULAR_LIMIT);
        for (Sale sale : get(popularSalesIds)) {
            salesMap.put(sale.getId(), sale);
        }

        List <Sale> popular = new ArrayList<>();
        for (Integer id : popularSalesIds) {
            popular.add(salesMap.get(id));
        }

        return popular;
    }

    public List<Sale> search(SearchCriteria criteria) throws Exception {
        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "WHERE s.sale_title LIKE ? " +
                "OR v.vendor_name LIKE ?";

        PreparedStatement stmt = mysql.prepareStatement(sql);
        String query = WILDCARD + criteria.getQuery() + WILDCARD;
        stmt.setString(1, query);
        stmt.setString(2, query);

        return getResults(stmt);
    }

    public List<Sale> getFavorites(User user) throws Exception {
        String sql =
                "SELECT s.*, v.* " +
                "FROM sales AS s " +
                "JOIN vendors AS v ON v.vendor_id = s.vendor_id " +
                "JOIN user_favorite_vendors AS ufv ON ufv.vendor_id = v.vendor_id " +
                "WHERE ufv.user_id = ?";

        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, user.getId());

        return getResults(stmt);
    }

    public double increaseViewCount(Sale sale) {
        return redis.zincrby(SALE_VIEWS_HASH, 1, Integer.toString(sale.getId()));
    }

    public static Sale hydrate(ResultSet rs) throws SQLException {
        Sale sale = new Sale();
        sale.setId(rs.getInt(Field.SALE_ID));
        sale.setTitle(rs.getString(Field.SALE_TITLE));
        sale.setExtra(rs.getString(Field.SALE_EXTRA));
        sale.setStartDate(rs.getDate(Field.SALE_START));
        sale.setEndDate(rs.getDate(Field.SALE_END));
        sale.setVendor(VendorsService.hydrate(rs));

        return sale;
    }

    private List<Sale> getResults(PreparedStatement stmt) throws SQLException {
        List<Sale> results = new ArrayList<>();
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            results.add(hydrate(rs));
        }

        return results;
    }

    private Set<Integer> getPopularSalesIds(int limit) {
        Set<Integer> ids = new LinkedHashSet<>();
        int offset = 0;
        for (String id : redis.zrevrange(SALE_VIEWS_HASH, offset, limit)) {
            ids.add(Integer.valueOf(id));
        }

        return ids;
    }

    private Set<Integer> findMissingSaleIds(List<Sale> result, Set<Integer> saleIds) {
        Set<Integer> foundSaleIds = new HashSet<>();
        Set<Integer> missingSaleIds = new HashSet<>();
        for (Sale sale : result) {
            foundSaleIds.add(sale.getId());
        }
        for (Integer saleId : saleIds) {
            if (!foundSaleIds.contains(saleId)) {
                missingSaleIds.add(saleId);
            }
        }
        return missingSaleIds;
    }

}
