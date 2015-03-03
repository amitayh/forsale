package forsale.server.ioc;

public interface ServiceProvider {

    public Object create(Container container) throws Exception;

}
