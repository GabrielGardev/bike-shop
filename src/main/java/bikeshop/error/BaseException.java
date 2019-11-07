package bikeshop.error;

public class BaseException extends RuntimeException {

    private int status;

    BaseException(int status, String message) {
        super(message);
        this.setStatus(status);
    }

    private void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
