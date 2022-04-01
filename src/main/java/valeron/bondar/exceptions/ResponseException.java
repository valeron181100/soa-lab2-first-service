package valeron.bondar.exceptions;

public class ResponseException extends Exception{

    private int httpCode = 500;

    public ResponseException(int httpCode, String message) {
        super(message);
        this.httpCode = httpCode;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }
}
