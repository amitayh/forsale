package forsale.server.servlet;

import forsale.server.domain.Email;
import forsale.server.domain.Gender;
import forsale.server.domain.User;
import forsale.server.service.AuthServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

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
        Logger logger = (Logger)get("logger");
        JsonResult result = new JsonResult();

        // prepare user object
        User user = new User();
        user.setEmail(new Email(request.getParameter("email")));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setGender(Gender.valueOf(request.getParameter("gender")));

        try {
            int userId = auth.register(user);
            logger.fine("Registering user id: " + userId);
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
