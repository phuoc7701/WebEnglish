package vn.edu.engzone.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Constraint(
        validatedBy = {DobValidator.class}
)
public @interface DobConstraint {

    // 3 thuộc tính cơ bản của 1 annotation dành cho validation
    String message() default "Invalid date of birth";

    int min();


    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
