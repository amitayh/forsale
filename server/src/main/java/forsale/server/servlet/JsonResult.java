package forsale.server.servlet;


public class JsonResult {

    public static final class ResponseCode {
        public static final String FAIL = "ERR";
        public static final String SUCCESS = "OK";
    }

    /**
     * Status code.
     */
    public String response_code;

    /**
     * Error message, if needed
     */
    public String error;

    /**
     * Result data (assuming no errors occurred)
     */
    public Object data;

    /**
     * Default constructor
     */
    public JsonResult() {
        this.error = null;
        this.response_code = ResponseCode.SUCCESS;
        this.data = null;
    }

    /**
     * Success json result
     *
     * @param data
     */
    public void success(Object data) {
        this.data = data;
        this.response_code = ResponseCode.SUCCESS;
        this.error = null;
    }

    /**
     * Fail json result
     *
     * @param message
     */
    public void fail(String message) {
        this.data = null;
        this.response_code = ResponseCode.FAIL;
        this.error = message;
    }


}
