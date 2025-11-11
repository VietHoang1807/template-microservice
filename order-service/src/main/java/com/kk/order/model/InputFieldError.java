package com.kk.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class InputFieldError {
    private String field;
    private String message;
}
