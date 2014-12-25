package forsale.server.servlet;

import forsale.server.domain.JsonResult;
import forsale.server.service.DbBuilderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminDbServlet", urlPatterns = {"/admin/db"})
public class AdminDbServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        DbBuilderService dbBuilder = (DbBuilderService) get("service.db-builder");
        JsonResult result = new JsonResult();

        try {
            // Authorization checks...
            dbBuilder.createTables();
        } catch (Exception e) {
            result.fail(e.getMessage());
        }

        writeJsonResult(response, result);
    }

}
