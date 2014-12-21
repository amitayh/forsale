package forsale.server.service;

import forsale.server.domain.*;
import forsale.server.service.exception.DuplicateEmailException;
import forsale.server.service.exception.InvalidCredentialsException;
import forsale.server.service.exception.MissingUserException;

import java.sql.*;
import java.util.Map;

public class UsersService {

    public static final class Field {
        public static final String USER_ID = "user_id";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_NAME = "user_name";
        public static final String USER_GENDER = "user_gender";
        public static final String USER_BIRTH_DATE = "user_birth_date";
    }

    final private Connection mysql;

    public UsersService(Connection mysql) {
        this.mysql = mysql;
    }

    public int insert(User user) throws Exception {
        String sql =
                "INSERT INTO users (user_email, user_password, user_name, user_gender, user_birth_date) " +
                "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Email email = user.getEmail();
        stmt.setString(1, email.toString());
        stmt.setString(2, user.getPassword().getHashedPassword());
        stmt.setString(3, user.getName());
        stmt.setString(4, user.getGender().toString());
        stmt.setDate(5, new Date(user.getBirthDath().getTime()));

        try {
            stmt.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            throw new DuplicateEmailException(email, e);
        }

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }

        return user.getId();
    }

    public User get(int userId) throws Exception {
        User user;

        String sql = "SELECT * FROM users WHERE user_id = ?";
        PreparedStatement st = mysql.prepareStatement(sql);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = hydrate(rs);
        } else {
            throw new MissingUserException(userId);
        }

        return user;
    }

    public User get(User.Credentials credentials) throws Exception {
        User user;

        String sql = "SELECT * FROM users WHERE user_email = ? AND user_password = ?";
        PreparedStatement st = mysql.prepareStatement(sql);
        st.setString(1, credentials.getEmail().toString());
        st.setString(2, credentials.getPassword().getHashedPassword());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = hydrate(rs);
        } else {
            throw new InvalidCredentialsException();
        }

        return user;
    }

    public void edit(User user) throws Exception {
        String sql = "UPDATE users " +
                "SET user_email = ?, user_password = ?, user_name = ?, user_gender = ?, user_birth_date = ? " +
                "WHERE user_id = ?";

        PreparedStatement stmt = mysql.prepareStatement(sql);

        stmt.setString(1, user.getEmail().toString());
        stmt.setString(2, user.getPassword().getHashedPassword());
        stmt.setString(3, user.getName());
        stmt.setString(4, user.getGender().toString());
        stmt.setDate(5, new Date(user.getBirthDath().getTime()));
        stmt.setInt(6, user.getId());

        try {
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Unable to edit user (" + user.getEmail().toString() +")", e);
        }
    }

    public void setUserFavoriteVendor(User user, Vendor vendor) throws Exception {
        String sql = "INSERT INTO user_favorite_vendors (user_id, vendor_id) VALUES (?, ?)";
        PreparedStatement stmt = mysql.prepareStatement(sql);
        stmt.setInt(1, user.getId());
        stmt.setInt(2, vendor.getId());

        try {
            stmt.execute();
        } catch (SQLException e) {
            throw new Exception("Unable to set user favorite vendor", e);
        }
    }

    public static User hydrate(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(Field.USER_ID));
        user.setEmail(new Email(rs.getString(Field.USER_EMAIL)));
        user.setName(rs.getString(Field.USER_NAME));
        user.setGender(Gender.valueOf(rs.getString(Field.USER_GENDER).toUpperCase()));
        user.setBirthDath(new BirthDate(rs.getDate(Field.USER_BIRTH_DATE).getTime()));

        return user;
    }

    public static User hydrate(Map<String, String> userHash) throws Exception {
        User user = new User();
        user.setId(Integer.valueOf(userHash.get(Field.USER_ID)));
        user.setEmail(new Email(userHash.get(Field.USER_EMAIL)));
        user.setName(userHash.get(Field.USER_NAME));
        user.setGender(Gender.valueOf(userHash.get(Field.USER_GENDER)));
        user.setBirthDath(new BirthDate(userHash.get(Field.USER_BIRTH_DATE)));

        return user;
    }
}
