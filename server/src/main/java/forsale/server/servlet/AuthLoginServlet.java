package forsale.server.servlet;

import forsale.server.domain.Email;
import forsale.server.domain.Password;
import forsale.server.domain.User;
import forsale.server.service.AuthService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

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

        AuthService auth = (AuthService) get("service.auth");
        JsonResult result = new JsonResult();

        Email email = new Email(request.getParameter("email"));
        Password password = new Password(request.getParameter("password"));
        User.Credentials credentials = new User.Credentials(email, password);

        try {
            HttpSession session = request.getSession();
            User user = auth.authenticate(credentials, session.getId());
            if (user == null) {
                // Failed to login user
                result.fail("Wrong email or password.");
            } else {
                // Succeed login user, return user's id
                result.success(user.getId());
            }
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }
}
