package forsale.server.service;

/**
 * Created by assafey on 12/2/14.
 */
public interface SessionsServiceInterface {

    public void setSessionId(String sessionId, Integer userId);

    public int getUserId(String sessionId);

}
