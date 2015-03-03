package forsale.server.domain;

public class JsonResult {

    public enum ResponseCode {OK, ERR}

    /**
     * Status code.
     */
    public ResponseCode response_code = ResponseCode.OK;

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
    public void success(Object data) {
        this.response_code = ResponseCode.OK;
        this.error = null;
        this.data = data;
    }

    /**
     * Fail json result
     */
    public void fail(String message) {
        this.response_code = ResponseCode.ERR;
        this.error = message;
        this.data = null;
    }

}
