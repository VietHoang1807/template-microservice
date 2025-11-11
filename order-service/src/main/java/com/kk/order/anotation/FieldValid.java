package com.kk.order.anotation;

import com.kk.order.validator.FieldValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE,
        ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FieldValidator.class})
public @interface FieldValid {

    String message() default "{notEmpty.message}";
    String field() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
