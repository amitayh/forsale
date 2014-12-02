package forsale.server.service;

import forsale.server.domain.BirthDate;
import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.User;
import forsale.server.service.exception.DuplicateEmailException;

import java.sql.*;

public class UsersService implements UsersServiceInterface {
    final private Connection mysql;

    public UsersService(Connection mysql) {
        this.mysql = mysql;
    }


    @Override
    public int insert(User user) throws Exception {
        String sql = "INSERT INTO users (user_email, user_password, user_name, user_gender, user_birth_date) " +
                        "VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Email email = user.getEmail();
        statement.setString(1, email.toString());
        statement.setString(2, user.getPassword().getHashedPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getGender().toString());
        statement.setDate(5, new Date(user.getBirthDath().getTime()));

        try {
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            String message = "Unable to insert user - email '" + email.toString() + "' already exists";
            throw new DuplicateEmailException(email, message, e);
        }

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }

        return user.getId();
    }

    @Override
    public User get(int userId) throws Exception {
        User user = null;

        String sql =
                "SELECT user_email, user_name, user_gender, user_birth_date " +
                        "FROM users WHERE user_id = ?";

        PreparedStatement st = mysql.prepareStatement(sql);
        st.setInt(1, userId);
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setId(userId);
            user.setEmail(new Email(rs.getString(1)));
            user.setName(rs.getString(2));
            user.setGender(Gender.valueOf(rs.getString(3).toUpperCase()));
            user.setBirthDath(new BirthDate(rs.getDate(4).getTime()));
        }

        return user;
    }

    @Override
    public void edit(User user) throws Exception {
        String sql = "UPDATE users " +
                "SET user_email = ?, user_password = ?, user_name = ?, user_gender = ?, user_birth_date = ? " +
                "WHERE user_id = ?";

        PreparedStatement statement = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(1, user.getEmail().toString());
        statement.setString(2, user.getPassword().getHashedPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getGender().toString());
        statement.setDate(5, new Date(user.getBirthDath().getTime()));
        statement.setInt(6, user.getId());

        try {
            statement.executeUpdate();
        } catch (Exception e) {
            throw new Exception("Unable to edit user (" + user.getEmail().toString() +")");
        }
    }
}
