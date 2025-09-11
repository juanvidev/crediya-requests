package co.com.crediya.api.exception;

import co.com.crediya.model.exception.CustomBaseException;

import java.util.List;

public class UnexpectedErrorException extends CustomBaseException {
    public UnexpectedErrorException(Throwable ex) {
        super(
            500,
            ex.getMessage(),
            "UNEXPECTED_ERROR",
            List.of()
        );

//        initCause(ex);
    }
}
