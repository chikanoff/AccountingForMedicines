package by.accounting.medicines.exception;

import lombok.Getter;

@Getter
public class DataExistException extends RuntimeException {
    private String code;

    public DataExistException(String message, String code) {
        super(message);
        this.code = code;
    }
}
