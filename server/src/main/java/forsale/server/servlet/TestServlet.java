package forsale.server.servlet;

import forsale.server.service.HelloService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TestServlet", urlPatterns = {"/test"})
public class TestServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HelloService service = (HelloService) get("service.hello");
        String name = request.getParameter("name");
        JsonResult result = new JsonResult(service.hello(name));
        writeJsonResult(response, result);
    }

}
