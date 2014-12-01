package forsale.server.servlet;

import com.google.gson.Gson;
import forsale.server.Bootstrap;
import forsale.server.dependencyinjection.Container;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseServlet extends HttpServlet {

    private static Container container;

    protected static Container getContainer() {
        if (container == null) {
            container = Bootstrap.createDependencyInjectionContainer();
        }
        return container;
    }

    protected Object get(String key) throws ServletException {
        Object value;
        try {
            value = getContainer().get(key);
        } catch (Exception e) {
            String message = "Exception thrown while getting key '" + key + "' from DI container";
            throw new ServletException(message, e);
        }
        return value;
    }

    protected void writeJsonResult(HttpServletResponse response, JsonResult result)
            throws ServletException, IOException {
        Gson gson = (Gson) get("gson");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        gson.toJson(result, response.getWriter());
    }

}
