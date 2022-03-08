package mz.co.app.api.configurations.security.exceptions;

public class PasswordValidationException extends RuntimeException {
    public PasswordValidationException() { super(); }
    public PasswordValidationException(String message) { super(message); }
}
