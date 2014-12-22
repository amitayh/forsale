package forsale.server;

import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

    public static String getMultipleParametersList(int count) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < count; i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");

        return sb.toString();
    }

    public static String md5(String str) throws NoSuchAlgorithmException {
        // Create MessageDigest instance for MD5
        MessageDigest md = MessageDigest.getInstance("MD5");

        //Add password bytes to digest
        md.update(str.getBytes());

        //Get the hash's bytes
        byte[] bytes = md.digest();

        //This bytes[] has bytes in decimal format;
        //Convert it to hexadecimal format
        StringBuilder sb = new StringBuilder();
        for (byte aByte : bytes) {
            sb.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
        }

        //Get complete hashed password in hex format
        return sb.toString();
    }

    public static URL getResource(String name) throws Exception {
        URL resource = Utils.class.getClassLoader().getResource(name);
        if (resource == null) {
            throw new Exception("Unable to locate resource '" + name + "'");
        }
        return resource;
    }

}
