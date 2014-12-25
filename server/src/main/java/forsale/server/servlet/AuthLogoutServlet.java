package forsale.server.servlet;

import forsale.server.domain.JsonResult;
import forsale.server.domain.User;
import forsale.server.events.Dispatcher;
import forsale.server.service.AuthService;
import forsale.server.service.event.UserLogoutEvent;

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
        Dispatcher dispatcher = (Dispatcher) get("dispatcher");
        JsonResult result = new JsonResult();

        try {
            HttpSession session = request.getSession();
            User user = auth.getUser(session);
            auth.logout(session);
            dispatcher.dispatch(new UserLogoutEvent(user));
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
