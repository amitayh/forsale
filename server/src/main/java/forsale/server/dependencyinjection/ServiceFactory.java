package forsale.server.dependencyinjection;

public interface ServiceFactory {

    public Object create(Container container) throws Exception;

}
