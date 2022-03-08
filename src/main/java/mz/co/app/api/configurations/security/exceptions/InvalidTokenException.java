package mz.co.app.api.configurations.security.exceptions;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() { super(); }
    public InvalidTokenException(String message) {
        super(message);
    }
}
