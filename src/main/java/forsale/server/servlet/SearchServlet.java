package forsale.server.servlet;

import forsale.server.domain.JsonResult;
import forsale.server.domain.Sale;
import forsale.server.service.SalesService;
import forsale.server.domain.SearchCriteria;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SearchServlet", urlPatterns = {"/search"})
public class SearchServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalesService sales = (SalesService) get("service.sales");
        JsonResult result = new JsonResult();

        try {
            String query = request.getParameter("query");
            SearchCriteria criteria = new SearchCriteria(query);
            List<Sale> popular = sales.search(criteria);
            result.success(popular);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
