package forsale.server.servlet;

import forsale.server.domain.*;
import forsale.server.service.AuthServiceInterface;
import forsale.server.service.UsersServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "UsersServlet", urlPatterns = "/users/profile")
public class ProfileServlet extends BaseServlet {

    /**
     * Post: when edit profile info
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        editProfile(request, response);
    }

    /**
     * Get: when just want to watch the profile info
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showProfile(request, response);
    }

    private void showProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersServiceInterface users = (UsersServiceInterface)get("service.users");
        AuthServiceInterface auth = (AuthServiceInterface)get("service.auth");
        JsonResult result = new JsonResult();

        try {
            HttpSession session = request.getSession();

            if (auth.isSessionExpired(session.getId())) {
                result.fail("Session Expired!");

            } else {
                int userId = auth.getUserId(session.getId());
                User user = users.get(userId);

                if (user == null) {
                    result.fail("User does not exist.");
                } else {
                    result.success(user);
                }
            }
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersServiceInterface users = (UsersServiceInterface)get("service.users");
        AuthServiceInterface auth = (AuthServiceInterface)get("service.auth");
        JsonResult result = new JsonResult();

        HttpSession session = request.getSession();

        if (auth.isSessionExpired(session.getId())) {
            result.fail("Session Expired!");

        } else {
            int userId = auth.getUserId(session.getId());

            User user = new User();
            user.setId(userId);
            user.setEmail(new Email(request.getParameter("email")));
            user.setPassword(new Password(request.getParameter("password")));
            user.setName(request.getParameter("name"));
            user.setGender(Gender.valueOf(request.getParameter("gender")));

            try {
                user.setBirthDath(new BirthDate(request.getParameter("birth")));
                users.edit(user);
                result.success(user); // can be anything and not necessary user as the success object...

            } catch (Exception e) {
                result.fail(e.getMessage());
            }
        }

        writeJsonResult(response, result);
    }

}
