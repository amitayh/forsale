package forsale.server.stub;

public class HelloService {

    final private String prefix;

    final private String suffix;

    public HelloService() {
        this("", "");
    }

    public HelloService(String prefix, String suffix) {
        this.prefix = prefix;
        this.suffix = suffix;
    }

    public String hello(String name) {
        return prefix + "Hello, " + name + suffix;
    }

}
