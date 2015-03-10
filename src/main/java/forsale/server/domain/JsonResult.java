package forsale.server.domain;

import javax.servlet.http.HttpServletResponse;

public class JsonResult {

    /**
     * Status code.
     */
    public int status = HttpServletResponse.SC_OK;

    /**
     * Error message, if needed
     */
    public String error;

    /**
     * Result data (assuming no errors occurred)
     */
    public Object data;

    /**
     * Success json result
     */
    public void success(Object data, int status) {
        this.status = status;
        this.error = null;
        this.data = data;
    }

    public void success(Object data) {
        success(data, HttpServletResponse.SC_OK);
    }

    /**
     * Fail json result
     */
    public void fail(String message, int status) {
        this.status = status;
        this.error = message;
        this.data = null;
    }

    public void fail(String message) {
        fail(message, HttpServletResponse.SC_BAD_REQUEST);
    }

}
