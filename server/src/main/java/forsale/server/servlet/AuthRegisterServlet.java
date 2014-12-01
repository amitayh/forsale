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

@WebServlet(name = "AuthRegisterServlet", urlPatterns = {"/auth/register"})
public class AuthRegisterServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthServiceInterface auth = (AuthServiceInterface) get("service.auth");
        JsonResult result = new JsonResult();

        User user = new User();
        user.setEmail(new Email(request.getParameter("email")));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setGender(Gender.valueOf(request.getParameter("gender")));

        try {
            result.data = auth.register(user);
        } catch (Exception e) {
            result.err = 1;
            result.message = e.getMessage();
        }

        writeJsonResult(response, result);
    }
}
