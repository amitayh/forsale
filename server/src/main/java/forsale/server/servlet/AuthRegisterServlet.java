package forsale.server.servlet;

import forsale.server.domain.*;
import forsale.server.service.AuthService;

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

        AuthService auth = (AuthService) get("service.auth");
        JsonResult result = new JsonResult();

        // prepare user object
        User user = new User();
        user.setEmail(new Email(request.getParameter("email").toLowerCase()));
        user.setPassword(new Password(request.getParameter("password")));
        user.setName(request.getParameter("name"));
        user.setGender(Gender.valueOf(request.getParameter("gender").toUpperCase()));

        try {
            user.setBirthDath(new BirthDate(request.getParameter("birth")));
            HttpSession session = request.getSession();
            int userId = auth.signup(user, session.getId());

            if (userId < 0) {
                // invalid user id
                result.fail("Failed to register.");
            } else {
                // new user registered :)
                result.success(userId);
            }
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }


}
