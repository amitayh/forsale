/***************************************************************************
 * File name: Log.java
 * Created by: Assaf Grimberg.
 * Description: This class will help logging the app
 * Change log:
 * [+] 29/11/2014 - Assaf Grimberg, file created.
 ***************************************************************************/

package forsale.server.utils;

import java.util.Calendar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Log {

    /*****************************************
     * Constants
     *****************************************/
    private static final boolean DEBUG_MODE = true;

    /*****************************************
     * Public methods
     *****************************************/

    public static void debug(String funcName, String message) {
        if (DEBUG_MODE) {
            String logMessege = "DEBUG-" + getTimestamp() + "> " + message + " IN " + funcName;
            System.out.println(logMessege);
        }
    }

    public static void info(String funcName, String message) {
        String logMessege = "INFO-" + getTimestamp() + "> " + message + " IN " + funcName;
        System.out.println(logMessege);
    }

    public static void error(String funcName, String message) {
        String logMessege = "ERROR-" + getTimestamp() + "> " + message + " IN " + funcName;
        System.out.println(logMessege);
    }

    public static String getMethodName() {
        final StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        return ste[ste.length - 2].getMethodName();
    }

    /*****************************************
     * Private methods
     *****************************************/

    private static String getTimestamp() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        return dateFormat.format(cal.getTime());
    }

}
