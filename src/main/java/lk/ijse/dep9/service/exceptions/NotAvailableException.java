package lk.ijse.dep9.service.exceptions;

public class NotAvailableException extends RuntimeException{
    public NotAvailableException(String message) {
        super(message);
    }

    public NotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
