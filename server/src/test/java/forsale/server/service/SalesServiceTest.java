package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class SalesServiceTest extends TestCase {

    final private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private SalesService sales;

    private Vendor defaultVendor;

    @Before
    public void setUp() throws Exception {
        flushMysql();
        flushRedis();

        sales = new SalesService(getMysql(), getRedis());
        defaultVendor = createVendor();
    }

    @After
    public void tearDown() {
        sales = null;
        defaultVendor = null;
    }

    @Test
    public void testInsertSale() throws Exception {
        Sale sale = new Sale();
        sale.setTitle("Sale #1");
        sale.setExtra("15% off");
        sale.setStartDate(dateFormat.parse("2014-10-09"));
        sale.setEndDate(dateFormat.parse("2014-10-09"));
        sale.setVendor(defaultVendor);
        int saleId = sales.insert(sale);

        assertEquals(saleId, sale.getId());
        assertNotNull(saleId);
        assertTrue(saleId > 0);
    }

    @Test
    public void testGetSalesByIdWithEmptySet() throws Exception {
        createSale("Sale #1");

        Set<Integer> ids = new HashSet<>();

        List<Sale> result = sales.getSalesByIds(ids);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testGetSalesById() throws Exception {
        // Insert sales
        Sale sale1 = createSale("Sale #1");
        Sale sale2 = createSale("Sale #2");
        Sale sale3 = createSale("Sale #3");

        Set<Integer> ids = new HashSet<>();
        ids.add(sale1.getId());
        ids.add(sale3.getId());

        List<Sale> result = sales.getSalesByIds(ids);
        assertEquals(2, result.size());
        assertEquals(sale1.getId(), result.get(0).getId());
        assertEquals(sale3.getId(), result.get(1).getId());
    }

    @Test
    public void testGetPopularReturnsSalesSortedByPopularity() throws Exception {
        // Insert sales
        Sale sale1 = createSale("Sale #1");
        Sale sale2 = createSale("Sale #2");
        Sale sale3 = createSale("Sale #3");

        // Set view count rank: sale2, sale3, sale1
        sales.increaseViewCount(sale1);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale2);
        sales.increaseViewCount(sale3);
        sales.increaseViewCount(sale3);

        List<Sale> popular = sales.getPopular();
        assertEquals(sale2.getId(), popular.get(0).getId()); // 1st
        assertEquals(sale3.getId(), popular.get(1).getId()); // 2nd
        assertEquals(sale1.getId(), popular.get(2).getId()); // 3rd
    }

    @Test
    public void testGetFavoriteSalesReturnsSalesFromUsersFavoriteCategories() throws Exception {
        // Insert sales
        Sale sale1 = createSale("Sale #1");

        Sale sale2 = new Sale();
        sale2.setTitle("Sale #2");
        sale2.setExtra("Sale #2");
        sale2.setStartDate(dateFormat.parse("2014-12-09"));
        sale2.setEndDate(dateFormat.parse("2014-12-09"));
        sale2.setVendor(createVendor("Other vendor"));
        sales.insert(sale2);

        Sale sale3 = createSale("Sale #3");

        // Create user
        UsersService users = (UsersService) container.get("service.users");
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDath(new BirthDate("1940-10-09"));
        users.insert(user);

        users.setUserFavoriteVendor(user, defaultVendor);

        List<Sale> favorites = sales.getFavorites(user);
        assertEquals(2, favorites.size());
        assertEquals(sale1.getId(), favorites.get(0).getId());
        assertEquals(sale3.getId(), favorites.get(1).getId());
    }

    @Test
    public void testGetSingleSale() throws Exception {
        Sale sale = createSale("Sale #1");
        assertEquals(sale, sales.get(sale.getId()));
    }

    @Test
    public void testGotRecentSalesInCorrectOrder() throws Exception {
        // Insert sales
        Sale sale1 = new Sale();
        sale1.setTitle("Sale #1");
        sale1.setExtra("15% off");
        sale1.setStartDate(dateFormat.parse("2014-10-09"));         // oldest
        sale1.setEndDate(dateFormat.parse("2014-10-09"));
        sale1.setVendor(defaultVendor);
        sales.insert(sale1);

        Sale sale2 = new Sale();
        sale2.setTitle("Sale #2");
        sale2.setExtra("15% off");
        sale2.setStartDate(dateFormat.parse("2014-11-09"));         // middle
        sale2.setEndDate(dateFormat.parse("2014-11-09"));
        sale2.setVendor(defaultVendor);
        sales.insert(sale2);

        Sale sale3 = new Sale();
        sale3.setTitle("Sale #3");
        sale3.setExtra("15% off");
        sale3.setStartDate(dateFormat.parse("2014-12-09"));         // newest
        sale3.setEndDate(dateFormat.parse("2014-12-09"));
        sale3.setVendor(defaultVendor);
        sales.insert(sale3);

        List<Sale> salesList = this.sales.getRecent();

        assertEquals(salesList.get(0).getId(), sale3.getId()); // newest
        assertEquals(salesList.get(1).getId(), sale2.getId()); // middle
        assertEquals(salesList.get(2).getId(), sale1.getId()); // oldest
    }

    @Test
    public void testSearchSalesByTitle() throws Exception {
        Sale sale1 = createSale("Save 15% on all T-shirts");
        Sale sale2 = createSale("Save 15% on all shoes");
        Sale sale3 = createSale("Save 15% on all shirts");

        SearchCriteria criteria = new SearchCriteria("shirts");
        List<Sale> searchResults = sales.search(criteria);

        assertEquals(2, searchResults.size());
        assertEquals(searchResults.get(0).getId(), sale1.getId());
        assertEquals(searchResults.get(1).getId(), sale3.getId());
    }

    @Test
    public void testSearchSalesByVendor() throws Exception {
        Sale sale1 = createSale("Save 15% on all T-shirts");
        Sale sale2 = createSale("Save 15% on all shoes");

        Vendor shoeVendor = createVendor("Magic shoes");
        Sale sale3 = createSale("AMAZING SALE!", shoeVendor);

        SearchCriteria criteria = new SearchCriteria("shoes");
        List<Sale> searchResults = sales.search(criteria);

        assertEquals(2, searchResults.size());
        assertEquals(searchResults.get(0).getId(), sale2.getId());
        assertEquals(searchResults.get(1).getId(), sale3.getId());
    }

    private Vendor createVendor() throws Exception {
        return createVendor("Default vendor");
    }

    private Vendor createVendor(String name) throws Exception {
        VendorsService vendors = (VendorsService) container.get("service.vendors");
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendors.insert(vendor);

        return vendor;
    }

    private Sale createSale(String title) throws Exception {
        return createSale(title, defaultVendor);
    }

    private Sale createSale(String title, Vendor vendor) throws Exception {
        Sale sale = new Sale();
        sale.setTitle(title);
        sale.setExtra(title + " - 15% off");
        sale.setStartDate(dateFormat.parse("2014-12-09"));
        sale.setEndDate(dateFormat.parse("2014-12-09"));
        sale.setVendor(vendor);
        sales.insert(sale);

        return sale;
    }

}