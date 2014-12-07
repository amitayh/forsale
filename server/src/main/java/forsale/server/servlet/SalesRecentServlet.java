package forsale.server.servlet;

import forsale.server.domain.Sale;
import forsale.server.service.SalesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "SalesRecentServlet")
public class SalesRecentServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalesService sales = (SalesService) get("service.sales");
        JsonResult result = new JsonResult();
        try {
            List<Sale> recent = sales.getRecent();
            result.success(recent);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
