package com.kk.order.validator;

import com.kk.order.anotation.FieldValid;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class FieldValidator implements ConstraintValidator<FieldValid, Object> {

    private String message;
    private String field;

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return (o != null && !o.toString().trim().isEmpty());
    }
}
