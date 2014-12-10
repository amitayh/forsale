package forsale.server.servlet;

import forsale.server.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthLogoutServlet", urlPatterns = {"/auth/logout"})
public class AuthLogoutServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthService auth = (AuthService) get("service.auth");
        JsonResult result = new JsonResult();

        try {
            HttpSession session = request.getSession();
            auth.logout(session);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
