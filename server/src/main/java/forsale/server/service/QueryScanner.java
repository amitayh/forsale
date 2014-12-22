package forsale.server.service;

import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

public class QueryScanner implements Iterable<String> {

    public static final String SQL_DELIMITER = ";";

    final private Scanner scanner;

    QueryScanner(URL resource) throws IOException {
        scanner = new Scanner(resource.openStream());
        scanner.useDelimiter(SQL_DELIMITER);
    }

    @Override
    public java.util.Iterator<String> iterator() {
        return new Iterator();
    }

    private class Iterator implements java.util.Iterator<String> {

        @Override
        public boolean hasNext() {
            return scanner.hasNext();
        }

        @Override
        public String next() {
            return scanner.next().trim();
        }

        @Override
        public void remove() {}

    }

}
