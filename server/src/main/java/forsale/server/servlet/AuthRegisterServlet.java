package forsale.server.servlet;

import forsale.server.domain.*;
import forsale.server.service.AuthService;
import forsale.server.service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "AuthRegisterServlet", urlPatterns = {"/auth/register"})
public class AuthRegisterServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersService users = (UsersService) get("service.users");
        AuthService auth = (AuthService) get("service.auth");
        HttpSession session = request.getSession();
        JsonResult result = new JsonResult();

        try {
            // Prepare user object
            User user = new User();
            user.setEmail(new Email(request.getParameter("email")));
            user.setPassword(new Password(request.getParameter("password")));
            user.setName(request.getParameter("name"));
            user.setGender(Gender.valueOf(request.getParameter("gender")));
            user.setBirthDath(new BirthDate(request.getParameter("birth")));

            users.insert(user);
            auth.login(user, session);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
