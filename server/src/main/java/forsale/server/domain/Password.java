package forsale.server.domain;

import forsale.server.service.Utils;

import java.security.NoSuchAlgorithmException;

final public class Password {

    public static final String SALT = "GaYxlftrLIiIHsWO";

    final private String hashedPassword;

    public Password(String password) {
        hashedPassword = hashPassword(password);
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    private String hashPassword(String password) {
        String hashed = null;
        try {
            hashed = Utils.md5(SALT + password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Password pass1 = (Password)o;

        return pass1.hashedPassword.equals(this.hashedPassword);
    }

    @Override
    public int hashCode() {
        return this.hashedPassword != null ? this.hashedPassword.hashCode() : 0;
    }

}
