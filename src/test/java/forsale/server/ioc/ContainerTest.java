package forsale.server.ioc;

import forsale.server.stub.HelloService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContainerTest {

    private Container container;

    @Before
    public void setUp() {
        container = new Container();
    }

    @After
    public void tearDown() {
        container = null;
    }

    @Test
    public void testSetSimpleValues() throws Exception {
        container.set("key1", "value1");
        container.set("key2", 5);
        assertEquals("value1", container.get("key1"));
        assertEquals(5, container.get("key2"));
    }

    @Test
    public void testDefaultValues() throws Exception {
        container.set("key1", "value1");
        assertEquals("value1", container.get("key1", "default"));
        assertEquals("default", container.get("key2", "default"));
    }

    @Test
    public void testRegisterService() throws Exception {
        container.set("service.hello", new ServiceProvider() {
            @Override
            public Object create(Container container) {
                return new HelloService();
            }
        });
        assertNotNull(container.get("service.hello"));
    }

    @Test
    public void testRegisterServiceReturnsSameInstance() throws Exception {
        container.set("service.hello", new ServiceProvider() {
            @Override
            public Object create(Container container) {
                return new HelloService();
            }
        });
        Object service1 = container.get("service.hello");
        Object service2 = container.get("service.hello");
        assertSame(service1, service2);
    }

    @Test
    public void testGetServiceParametersFromContainer() throws Exception {
        container.set("hello.prefix", "Message: ");
        container.set("hello.suffix", "!");
        container.set("service.hello", new ServiceProvider() {
            @Override
            public Object create(Container container) throws Exception {
                String prefix = (String) container.get("hello.prefix");
                String suffix = (String) container.get("hello.suffix");
                return new HelloService(prefix, suffix);
            }
        });
        HelloService service = (HelloService) container.get("service.hello");
        assertEquals("Message: Hello, John!", service.hello("John"));
    }

}