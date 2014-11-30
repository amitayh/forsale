package forsale.server.dependencyinjection;

public interface ServiceProvider {

    public Object create(Container container) throws Exception;

}
