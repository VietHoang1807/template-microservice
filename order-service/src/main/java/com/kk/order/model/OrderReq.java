package com.kk.order.model;

import com.kk.order.anotation.FieldValid;

public record OrderReq(
        @FieldValid(message = "{validation.notEmpty}",field = "order.email")
        String email
) {
}
