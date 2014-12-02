package forsale.server.service;

import forsale.server.domain.BirthDate;
import forsale.server.domain.Gender;
import forsale.server.domain.User;

import java.sql.*;

public class AuthService implements AuthServiceInterface {

    final private Connection mysql;
    final private UsersServiceInterface usersService;

    public AuthService(Connection mysql, UsersServiceInterface usersService) {
        this.usersService = usersService;
        this.mysql = mysql;
    }

    @Override
    public int signup(User user) throws Exception {
        return this.usersService.insert(user);
    }

    @Override
    public User authenticate(User.Credentials credentials) throws Exception {
        User user = null;

        String sql =
                "SELECT user_id, user_name, user_gender, user_birth_date " +
                "FROM users WHERE user_email = ? AND user_password = ?";

        PreparedStatement st = mysql.prepareStatement(sql);
        st.setString(1, credentials.getEmail().toString());
        st.setString(2, credentials.getPassword().getHashedPassword());
        ResultSet rs = st.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt(1));
            user.setEmail(credentials.getEmail());
            user.setPassword(credentials.getPassword());
            user.setName(rs.getString(2));
            user.setGender(Gender.valueOf(rs.getString(3).toUpperCase()));
            user.setBirthDath(new BirthDate(rs.getDate(4).getTime()));
        }

        return user;
    }
}
