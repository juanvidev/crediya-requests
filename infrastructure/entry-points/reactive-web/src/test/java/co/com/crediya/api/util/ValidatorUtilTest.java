
package co.com.crediya.api.util;

import co.com.crediya.api.exception.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

class ValidatorUtilTest {
    private ValidatorUtil validatorUtil;

    @BeforeEach
    void setup() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            validatorUtil = new ValidatorUtil(validator);
        }
    }

    static class TestDTO {
        @NotNull
        String name;
        TestDTO(String name) { this.name = name; }
    }

    @Test
    @DisplayName("Should pass validation when object is valid")
    void validateValidObject() {
        TestDTO dto = new TestDTO("Juan Villota");

        StepVerifier.create(validatorUtil.validate(dto))
                .expectNext(dto)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should throw ValidationException when object is invalid")
    void validateInvalidObject() {
        TestDTO dto = new TestDTO(null);

        StepVerifier.create(validatorUtil.validate(dto))
                .expectError(ConstraintViolationException.class)
                .verify();
    }
}
