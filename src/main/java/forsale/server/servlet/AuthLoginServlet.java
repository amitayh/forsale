package forsale.server.servlet;

import forsale.server.domain.Email;
import forsale.server.domain.JsonResult;
import forsale.server.domain.Password;
import forsale.server.domain.User;
import forsale.server.events.Dispatcher;
import forsale.server.service.AuthService;
import forsale.server.service.UsersService;
import forsale.server.service.event.UserLoginEvent;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthLoginServlet", urlPatterns = {"/auth/login"})
public class AuthLoginServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersService users = (UsersService) get("service.users");
        AuthService auth = (AuthService) get("service.auth");
        Dispatcher dispatcher = (Dispatcher) get("dispatcher");
        JsonResult result = new JsonResult();

        try {
            Email email = new Email(request.getParameter("email"));
            Password password = new Password(request.getParameter("password"));
            User.Credentials credentials = new User.Credentials(email, password);

            User user = users.get(credentials);
            HttpSession session = request.getSession();
            auth.login(user, session);
            dispatcher.dispatch(new UserLoginEvent(user));
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        writeJsonResult(response, new JsonResult());
    }

}
