package forsale.server.service;

import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.User;
import forsale.server.service.exception.DuplicateEmailException;

import java.sql.*;

public class AuthService implements AuthServiceInterface {

    final private Connection mysql;

    public AuthService(Connection mysql) {
        this.mysql = mysql;
    }

    @Override
    public int signup(User user) throws Exception {
        String sql = "INSERT INTO users (user_email, user_password, user_name, user_gender, user_birth_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        Email email = user.getEmail();
        statement.setString(1, email.toString());
        statement.setString(2, user.getPassword());
        statement.setString(3, user.getName());
        statement.setString(4, user.getGender().toString());
        statement.setDate(5, new Date(user.getBirthDath().getTime()));

        try {
            statement.executeUpdate();
        } catch (SQLIntegrityConstraintViolationException e) {
            String message = "Unable to signup user - email '" + email.toString() + "' already exists";
            throw new DuplicateEmailException(email, message, e);
        }

        ResultSet rs = statement.getGeneratedKeys();
        if (rs.next()) {
            user.setId(rs.getInt(1));
        }

        return user.getId();
    }

    @Override
    public User authenticate(User.Credentials credentials) throws Exception {
        User user = null;

        String sql =
                "SELECT user_id, user_name, user_gender, user_birth_date " +
                "FROM users WHERE user_email = ? AND user_password = ?";

        PreparedStatement st = mysql.prepareStatement(sql);
        st.setString(1, credentials.getEmail().toString());
        st.setString(2, credentials.getPassword());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt(1));
            user.setEmail(credentials.getEmail());
            user.setPassword(credentials.getPassword());
            user.setName(rs.getString(2));
            user.setGender(Gender.valueOf(rs.getString(3).toUpperCase()));
            user.setBirthDath(rs.getDate(4));
        }

        return user;
    }
}
