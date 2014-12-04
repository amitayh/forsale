package forsale.server.service;

import java.util.Collection;

public class Utils {

    public static String getMultipleParametersList(Collection values) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append("?");
        }
        sb.append(")");

        return sb.toString();
    }

}
