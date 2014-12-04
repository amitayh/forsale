package forsale.server.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthDate {

    private Date birthDate;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public BirthDate(String dateString) throws Exception {
        this.birthDate = dateFormat.parse(dateString);
    }

    public BirthDate(long time) {
        this.birthDate = new Date(time);
    }

    public long getTime() {
        return this.birthDate.getTime();
    }

    @Override
    public String toString() {
        return dateFormat.format(this.birthDate);
    }

}
