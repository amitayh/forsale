package forsale.server.servlet;

import forsale.server.domain.Sale;
import forsale.server.domain.User;
import forsale.server.service.AuthService;
import forsale.server.service.SalesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SalesPopularServlet", urlPatterns = {"/sales/favorites"})
public class SalesFavoritesServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        AuthService auth = (AuthService) get("service.auth");
        SalesService sales = (SalesService) get("service.sales");

        JsonResult result = new JsonResult();
        try {
            User user = auth.getUser(request.getSession());
            List<Sale> favorites = sales.getFavorites(user);
            result.success(favorites);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
