package forsale.server.servlet;

import forsale.server.domain.Email;
import forsale.server.domain.User;
import forsale.server.domain.UserCredentials;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthServiceInterface auth = (AuthServiceInterface) get("service.auth");
        Logger logger = (Logger) get("logger");
        JsonResult result = new JsonResult();

        Email email = new Email(request.getParameter("email"));
        String password = request.getParameter("password");
        UserCredentials credentials = new UserCredentials(email, password);

        try {
            logger.info("Attempting login for '" + email.getValue() + "'");
            User user = auth.authenticate(credentials);
            // TODO save to session etc.
        } catch (Exception e) {
            logger.info("Login failed!");
            result.err = 1;
            result.message = e.getMessage();
        }

        writeJsonResult(response, result);
    }
}
