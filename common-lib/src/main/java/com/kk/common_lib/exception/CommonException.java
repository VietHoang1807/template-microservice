package com.kk.common_lib.exception;


import com.kk.common_lib.response.Response;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommonException extends RuntimeException {
    private Response response;
    private String message;

    public CommonException() {
    }

    @Deprecated
    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public CommonException(Response response, String message) {
        super(message);
        this.response = response;
        this.message = message == null ? response.getResponseMessage() : message;
    }

    public CommonException(Response response) {
        super(response.getResponseMessage());
        this.response = response;
        this.message = response.getResponseMessage();
    }

    public CommonException(String responseCode, String message) {
        super(message);
        this.response.setResponseCode(responseCode);
        this.response.setResponseMessage(message);
        this.message = message == null ? response.getResponseMessage() : message;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("{");
        stringBuilder.append("\"responseCode\":").append("\"").append(this.getResponse().getResponseCode()).append("\"").append(",");
        stringBuilder.append("\"responseMessage\":").append("\"").append(this.getMessage() == null ? this.getResponse().getResponseMessage() : this.getMessage()).append("\"");
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
