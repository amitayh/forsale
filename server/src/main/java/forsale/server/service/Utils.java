package forsale.server.service;

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

}
