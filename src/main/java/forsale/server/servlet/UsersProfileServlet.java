package forsale.server.servlet;

import forsale.server.domain.*;
import forsale.server.service.UsersService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UsersProfileServlet", urlPatterns = "/users/profile")
public class UsersProfileServlet extends BaseServlet {

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
        JsonResult result = new JsonResult();

        try {
            User user = getUser(request.getSession());
            result.success(user);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

    private void editProfile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UsersService users = (UsersService) get("service.users");
        JsonResult result = new JsonResult();

        try {
            // Update fields
            User user = getUser(request.getSession());
            user.setEmail(new Email(request.getParameter("email")));
            user.setPassword(new Password(request.getParameter("password")));
            user.setName(request.getParameter("name"));
            user.setGender(Gender.valueOf(request.getParameter("gender")));
            user.setBirthDate(new BirthDate(request.getParameter("birth")));

            // Save
            users.edit(user);
            result.success(user); // can be anything and not necessary user as the success object...
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
