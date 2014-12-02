package forsale.server.servlet;

import forsale.server.domain.Email;
import forsale.server.domain.User;
import forsale.server.service.AuthServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "AuthLoginServlet", urlPatterns = {"/auth/login"})
public class AuthLoginServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        login(request, response);
    }

    private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        AuthServiceInterface auth = (AuthServiceInterface)get("service.auth");
        Logger logger = (Logger)get("logger");
        JsonResult result = new JsonResult();

        Email email = new Email(request.getParameter("email"));
        String password = request.getParameter("password");
        User.Credentials credentials = new User.Credentials(email, password);

        try {
            logger.fine("Attempting login for '" + email.toString() + "'");
            User user = auth.authenticate(credentials);
            if (user == null) {
                // Failed to login user
                logger.warning("Failed to login for '" + email.toString() + "'");
                result.fail("Wrong email or password.");
            } else {
                // Succeed login user, return user's id
                result.success(user.getId());
            }
            // TODO save to session etc.
        } catch (Exception e) {
            logger.severe("Failed to login with exception: " + e.getMessage());
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }
}