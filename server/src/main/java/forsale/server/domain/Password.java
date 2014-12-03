package forsale.server.domain;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Password {
    private String hashedPassword;

    public Password() { this.hashedPassword = null; }

    public Password(String password) {
        this.hashedPassword = toHashPassword(password);
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return this.hashedPassword;
    }

    public String toHashPassword(String password) {
        String generatedPassword = null;

        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Add password bytes to digest
            md.update(password.getBytes());

            //Get the hash's bytes
            byte[] bytes = md.digest();

            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length ; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }

            //Get complete hashed password in hex format
            generatedPassword = sb.toString();

        } catch (NoSuchAlgorithmException e)  {
            e.printStackTrace();
        }

        return generatedPassword;
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
