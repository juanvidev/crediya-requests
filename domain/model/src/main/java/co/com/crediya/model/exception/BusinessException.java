package co.com.crediya.model.exception;

public class BusinessException extends CustomBaseException {
    public BusinessException(String businessError, String message) {
        super(
            400,
            message,
            businessError
        );
    }
}
