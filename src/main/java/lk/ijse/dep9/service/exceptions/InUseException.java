package lk.ijse.dep9.service.exceptions;

public class InUseException extends RuntimeException{
    public InUseException(String message) {
        super(message);
    }

    public InUseException(String message, Throwable cause) {
        super(message, cause);
    }
}
