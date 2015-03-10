package forsale.server.servlet;

import com.google.gson.Gson;
import forsale.server.Bootstrap;
import forsale.server.ioc.Container;
import forsale.server.domain.JsonResult;
import forsale.server.domain.User;
import forsale.server.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class BaseServlet extends HttpServlet {

    private static Container container;

    protected Object get(String key) throws ServletException {
        Object value;
        try {
            value = getContainer().get(key);
        } catch (Exception e) {
            String message = "Exception thrown while getting key '" + key + "' from IoC container";
            throw new ServletException(message, e);
        }
        return value;
    }

    protected User getUser(HttpSession session) throws Exception {
        AuthService auth = (AuthService) get("service.auth");
        return auth.getUser(session);
    }

    protected void writeJsonResult(HttpServletResponse response, JsonResult result)
            throws ServletException, IOException {
        Gson gson = (Gson) get("gson");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(result.status);
        gson.toJson(result, response.getWriter());
    }

    private static Container getContainer() {
        if (container == null) {
            container = Bootstrap.createIocContainer();
        }
        return container;
    }

}
