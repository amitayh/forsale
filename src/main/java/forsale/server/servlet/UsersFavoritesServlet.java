package forsale.server.servlet;

import forsale.server.Utils;
import forsale.server.domain.JsonResult;
import forsale.server.domain.User;
import forsale.server.domain.Vendor;
import forsale.server.service.UsersService;
import forsale.server.service.VendorsService;
import forsale.server.service.exception.SessionExpiredException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebServlet(name = "UsersFavoritesServlet", urlPatterns = "/users/favorites")
public class UsersFavoritesServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersService users = (UsersService) get("service.users");
        JsonResult result = new JsonResult();

        try {
            User user = getUser(request.getSession());
            List<Vendor> favoriteVendors = users.getUserFavoriteVendors(user);
            result.success(favoriteVendors);
        } catch (SessionExpiredException e) {
            result.fail(e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        UsersService users = (UsersService) get("service.users");
        VendorsService vendors = (VendorsService) get("service.vendors");
        JsonResult result = new JsonResult();

        try {
            User user = getUser(request.getSession());
            String[] vendorIdsRaw = request.getParameterValues("vendor_id");
            Set<Integer> vendorIds = new HashSet<>(Utils.convertIds(vendorIdsRaw));
            List<Vendor> selectedVendors = vendors.get(vendorIds);
            users.setUserFavoriteVendors(user, selectedVendors);
        } catch (SessionExpiredException e) {
            result.fail(e.getMessage(), HttpServletResponse.SC_UNAUTHORIZED);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
