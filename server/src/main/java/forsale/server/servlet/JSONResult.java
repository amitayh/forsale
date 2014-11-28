package forsale.server.servlet;

public class JsonResult {

    public static final int ERR_OK = 0;

    /**
     * Status code. 0 is OK, use other codes for identifying errors
     */
    public int err = ERR_OK;

    /**
     * Error message, if needed
     */
    public String message;

    /**
     * Result data (assuming no errors occurred)
     */
    public Object data;

    /**
     * Default constructor
     */
    public JsonResult() {
    }

    /**
     * Success constructor
     *
     * @param data
     */
    public JsonResult(Object data) {
        this.data = data;
    }

    /**
     * Error constructor
     *
     * @param err
     * @param message
     */
    public JsonResult(int err, String message) {
        this.err = err;
        this.message = message;
    }

}
