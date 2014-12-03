package forsale.server.domain;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BirthDate {
    private Date birthDate;

    public BirthDate(String dateString) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(this.birthDate);
    }
}
