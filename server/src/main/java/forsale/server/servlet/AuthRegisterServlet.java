package forsale.server.servlet;

import forsale.server.domain.*;
import forsale.server.service.AuthServiceInterface;
import forsale.server.service.SessionsServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthRegisterServlet", urlPatterns = {"/auth/register"})
public class AuthRegisterServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        register(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        register(request, response);
    }

    private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuthServiceInterface auth = (AuthServiceInterface)get("service.auth");
        SessionsServiceInterface sessions = (SessionsServiceInterface)get("service.sessions");
        JsonResult result = new JsonResult();

        // prepare user object
        User user = new User();
        user.setEmail(new Email(request.getParameter("email")));
        user.setPassword(new Password(request.getParameter("password")));
        user.setName(request.getParameter("name"));
        user.setGender(Gender.valueOf(request.getParameter("gender")));

        try {

            user.setBirthDath(new BirthDate(request.getParameter("birth")));

            int userId = auth.signup(user);
            if (userId < 0) {
                // invalid user id
                result.fail("Failed to register.");
            } else {
                // save new session id
                HttpSession session = request.getSession();
                sessions.setSessionId(session.getId(), user.getId());

                // new user registered :)
                result.success(userId);
            }
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }


}
