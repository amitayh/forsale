/***************************************************************************
 * File name: ForsaleServlet.java
 * Created by: Assaf Grimberg.
 * Description: This servlet is an interface for all 4$ale's servlets
 * Change log:
 * [+] 29/11/2014 - Assaf Grimberg, file created.
 ***************************************************************************/

package forsale.server.servlet;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ForsaleServlet")
public class ForsaleServlet extends HttpServlet {

    /*****************************************
     * Protected methods
     *****************************************/

    protected String toJsonError(String errorMessage) {
        JSONObject json = getJsonError();
        json.put("error", errorMessage);
        return json.toJSONString();
    }

    protected String toJsonOk(String[] data) {
        JSONObject json = getJsonOk();
        JSONArray dataArray = new JSONArray();
        for (String str : data) {
            dataArray.add(str);
        }
        json.put("data", dataArray);
        return json.toJSONString();
    }

    protected String toJsonOk(String data) {
        JSONObject json = getJsonOk();
        json.put("data", data);
        return json.toJSONString();
    }

    protected String toJsonOk(int data) {
        JSONObject json = getJsonOk();
        json.put("data", new Integer(data));
        return json.toJSONString();
    }

    protected void writeResponse(HttpServletResponse response, String answer) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write(answer);
    }

    /*****************************************
     * Private methods
     *****************************************/

    private JSONObject getJsonOk() {
        JSONObject json = new JSONObject();
        json.put("response_code", "OK");
        json.put("error", "");
        return json;
    }

    private JSONObject getJsonError() {
        JSONObject json = new JSONObject();
        json.put("response_code", "ERR");
        json.put("data", "");
        return json;
    }
}
