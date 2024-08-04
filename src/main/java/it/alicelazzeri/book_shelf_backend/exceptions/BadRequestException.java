package it.alicelazzeri.book_shelf_backend.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    private List<ObjectError> exceptionsList;

    public BadRequestException (List<ObjectError> exceptionsList) {
        this.exceptionsList = exceptionsList;
    }

    public BadRequestException(String message) {
        super(message);
    }

    public List<ObjectError> getExceptionsList() {
        return exceptionsList;
    }

    public void setExceptionsList(List<ObjectError> exceptionsList) {
        this.exceptionsList = exceptionsList;
    }
}
