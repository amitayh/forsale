package forsale.server.servlet;

import forsale.server.domain.Sale;
import forsale.server.service.SalesServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SingleSaleServlet")
public class SingleSaleServlet extends BaseServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showSale(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showSale(request, response);
    }

    private void showSale(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SalesServiceInterface sales = (SalesServiceInterface)get("service.sales");
        JsonResult result = new JsonResult();

        try {
            Sale sale = sales.get(new Integer(request.getParameter("sale_id")));
            if (sale != null) {
                result.success(sale);
            } else {
                result.fail("Wrong sale id");
            }
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }
}
