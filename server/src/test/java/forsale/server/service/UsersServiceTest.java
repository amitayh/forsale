package forsale.server.service;

import forsale.server.TestCase;
import forsale.server.domain.*;
import forsale.server.service.exception.DuplicateEmailException;
import forsale.server.service.exception.InvalidCredentialsException;
import forsale.server.service.exception.MissingUserException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UsersServiceTest extends TestCase {

    private VendorsService vendors;

    private UsersService users;

    @Before
    public void setUp() throws Exception {
        flushMysql();

        vendors = (VendorsService) container.get("service.vendors");
        users = new UsersService(getMysql());
    }

    @After
    public void tearDown() {
        vendors = null;
        users = null;
    }

    @Test
    public void testUserInsertedSameAsUserGotById() throws Exception {
        // prepare new user
        User user = createUser();
        int userId = user.getId();

        // validate user id is positive
        assertTrue(userId >= 0);

        // get the inserted user by id
        User insertedUser = users.get(userId);

        // validate same user
        assertNotNull(insertedUser);
        assertEquals(user, insertedUser);
    }

    @Test(expected=DuplicateEmailException.class)
    public void testCantInsertTwoUsersWithSameEmail() throws Exception {
        // Insert two users with same email - should throw an exception
        createUser();
        createUser();
    }

    @Test(expected = MissingUserException.class)
    public void testGetUserByIdFailure() throws Exception {
        users.get(1);
    }

    @Test
    public void testGetUserByIdSuccess() throws Exception {
        User user = createUser();
        User fetchedUser = users.get(user.getId());
        assertEquals(user, fetchedUser);
    }

    @Test(expected = InvalidCredentialsException.class)
    public void testGetUserByCredentialsFailure() throws Exception {
        Email email = new Email("john@beatles.com");
        Password password = new Password("123");
        users.get(new User.Credentials(email, password));
    }

    @Test
    public void testGetUserByCredentialsSuccess() throws Exception {
        User user = createUser();
        Email email = user.getEmail();
        Password password = user.getPassword();
        User fetchedUser = users.get(new User.Credentials(email, password));
        assertEquals(user, fetchedUser);
    }

    @Test
    public void testUserEditSucceed() throws Exception {
        // prepare new user
        User user = createUser();

        // edit user name
        String newName = "Ringo Starr";
        user.setName(newName);
        users.edit(user);

        // get edited user
        User editedUser = users.get(user.getId());

        assertNotNull(editedUser);
        assertEquals(user, editedUser);
        assertEquals(newName, editedUser.getName());
    }

    @Test
    public void testUserFavoriteVendors() throws Exception {
        User user = createUser();
        Vendor vendor1 = createVendor("Vendor #1");
        Vendor vendor2 = createVendor("Vendor #2");
        Vendor vendor3 = createVendor("Vendor #3");

        users.setUserFavoriteVendors(user, Arrays.asList(vendor1, vendor3));

        List<Vendor> favoriteVendors = users.getUserFavoriteVendors(user);
        assertEquals(2, favoriteVendors.size());
        assertEquals(vendor1, favoriteVendors.get(0));
        assertEquals(vendor3, favoriteVendors.get(1));
    }

    @Test
    public void testSetUserFavoriteVendorsClearsOldFavorites() throws Exception {
        User user = createUser();
        Vendor vendor1 = createVendor("Vendor #1");
        Vendor vendor2 = createVendor("Vendor #2");
        Vendor vendor3 = createVendor("Vendor #3");

        users.setUserFavoriteVendors(user, Arrays.asList(vendor1, vendor2));
        users.setUserFavoriteVendors(user, Arrays.asList(vendor3));

        List<Vendor> favoriteVendors = users.getUserFavoriteVendors(user);
        assertEquals(1, favoriteVendors.size());
        assertEquals(vendor3, favoriteVendors.get(0));
    }

    private User createUser() throws Exception {
        User user = new User();
        user.setName("John Lennon");
        user.setEmail(new Email("john@beatles.com"));
        user.setGender(Gender.MALE);
        user.setPassword(new Password("123"));
        user.setBirthDate(new BirthDate("1940-10-09"));
        users.insert(user);

        return user;
    }

    private Vendor createVendor(String name) throws Exception {
        Vendor vendor = new Vendor();
        vendor.setName(name);
        vendors.insert(vendor);

        return vendor;
    }

}