package lk.ijse.dep9.service.exceptions;

public class LimitExceedException extends RuntimeException{
    public LimitExceedException(String message) {
        super(message);
    }

    public LimitExceedException(String message, Throwable cause) {
        super(message, cause);
    }
}
