package co.com.crediya.api.exception;

import co.com.crediya.model.exception.CustomBaseException;

import java.util.List;

public class ConstraintViolationException extends CustomBaseException {
    public ConstraintViolationException( List<String> violations ) {
        super(
            "CONSTRAINT_VIOLATION",
            "Validation failed",
            400,
            violations
        );
    }
}
