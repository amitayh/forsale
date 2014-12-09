package forsale.server.domain;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

final public class BirthDate {

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    final private Date birthDate;

    public BirthDate(String dateString) throws Exception {
        birthDate = dateFormat.parse(dateString);
    }

    public BirthDate(long time) {
        birthDate = new Date(time);
    }

    public long getTime() {
        return birthDate.getTime();
    }

    @Override
    public String toString() {
        return dateFormat.format(birthDate);
    }

}
