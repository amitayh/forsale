package forsale.server.servlet;

import forsale.server.service.SalesServiceInterface;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SalesPopularServlet extends BaseServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        SalesServiceInterface sales = (SalesServiceInterface) get("service.sales");
    }

}
