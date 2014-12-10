package forsale.server.servlet;

import forsale.server.domain.Sale;
import forsale.server.service.SalesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SalesShowServlet", urlPatterns = "/sales/show")
public class SalesShowServlet extends BaseServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalesService sales = (SalesService) get("service.sales");
        JsonResult result = new JsonResult();

        try {
            Integer saleId = Integer.valueOf(request.getParameter("sale_id"));
            Sale sale = sales.get(saleId);
            result.success(sale);
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
