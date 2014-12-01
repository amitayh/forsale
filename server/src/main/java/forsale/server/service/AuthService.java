package forsale.server.service;

import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class AuthService implements AuthServiceInterface {

    final private Connection mysql;

    private final int USER_ID_INDEX = 0;
    private final int EMAIL_INDEX = 1;
    private final int PASSWORD_INDEX = 2;
    private final int NAME_INDEX = 3;
    private final int GENDER_INDEX = 4;
    private final int BIRTH_INDEX = 5;

    public AuthService(Connection mysql) {
        this.mysql = mysql;
    }

    @Override
    public int signup(User user) throws Exception {
        String sql = "INSERT INTO users (user_email, user_password, user_name, user_gender, user_birth_date) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = mysql.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        statement.setString(EMAIL_INDEX, user.getEmail().toString());
        statement.setString(PASSWORD_INDEX, user.getPassword());
        statement.setString(NAME_INDEX, user.getName());
        statement.setInt(GENDER_INDEX, user.getGender().ordinal());
        statement.setDate(BIRTH_INDEX, new java.sql.Date(user.getBirthDath().getTime()));
        statement.executeUpdate();
        ResultSet rs = statement.getGeneratedKeys();

        if (rs != null) {
            user.setId(rs.getInt(USER_ID_INDEX));
        } else {
            user.setId(-1);
        }

        return user.getId();
    }

    @Override
    public User authenticate(User.Credentials credentials) throws Exception {
        String sql = "SELECT * FROM users WHERE user_password=" + credentials.getPassword() + " AND user_email=" +
                credentials.getEmail().toString();

        Statement st = mysql.createStatement();

        User user = null;
        for (ResultSet rs = st.executeQuery(sql); rs.next(); ) {
            user = new User();
            user.setId(rs.getInt(USER_ID_INDEX));
            user.setPassword(""); // no need to return password (?)
            user.setEmail(new Email(rs.getString(EMAIL_INDEX)));
            Integer gender = new Integer(rs.getInt(GENDER_INDEX));
            user.setGender(Gender.valueOf(gender.toString()));
            user.setBirthDath(rs.getDate(BIRTH_INDEX));
            user.setName(rs.getString(NAME_INDEX));
        }

        return user;
    }
}
