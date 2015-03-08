package forsale.server.servlet;

import forsale.server.domain.JsonResult;
import forsale.server.service.VendorsService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "VendorsListServlet", urlPatterns = {"/vendors/list"})
public class VendorsListServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        VendorsService vendors = (VendorsService) get("service.vendors");
        JsonResult result = new JsonResult();

        try {
            result.success(vendors.getAll());
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
