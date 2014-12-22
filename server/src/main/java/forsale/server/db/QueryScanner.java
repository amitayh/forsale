package forsale.server.db;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

public class QueryScanner implements Iterable<String> {

    public static final String SQL_DELIMITER = ";";

    final private Scanner scanner;

    public QueryScanner(URL resource) throws IOException {
        scanner = new Scanner(resource.openStream());
        scanner.useDelimiter(SQL_DELIMITER);
    }

    @Override
    public Iterator<String> iterator() {
        return new QueryScannerIterator();
    }

    private class QueryScannerIterator implements Iterator<String> {

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
