package by.accounting.medicines.auth.jwt.model.exception;

public class JwtInitializationException extends RuntimeException {
    public JwtInitializationException(Throwable e) {
        super("Something went wrong while reading private key!", e);
    }
}
