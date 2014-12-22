package forsale.server.db;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class QueryScannerTest {

    @Test
    public void testIterator() throws Exception {
        URL test = getClass().getClassLoader().getResource("test.sql");
        QueryScanner scanner = new QueryScanner(test);
        List<String> queries = Lists.newArrayList(scanner);
        assertEquals(3, queries.size());
        assertEquals("CREATE TABLE test (id INT NOT NULL)", queries.get(0));
        assertEquals("INSERT INTO test (id) VALUES (1), (2), (3)", queries.get(1));
        assertEquals("SELECT * FROM test", queries.get(2));
    }

}