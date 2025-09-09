package co.com.crediya.api.exception;

import co.com.crediya.model.exception.CustomBaseException;

import java.util.List;

public class UnexpectedErrorException extends CustomBaseException {
    public UnexpectedErrorException(Throwable ex) {
        super(
            "UNEXPECTED_ERROR",
            ex.getMessage(),
            500,
            List.of()
        );

//        initCause(ex);
    }
}
