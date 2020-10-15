package com.qaprosoft.zafira.enums;

public enum HTTPStatusCodeType {
    OK(200),
    CREATED(201),
    NO_CONTENT(204),
    ACCEPTED(202),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    NOT_FOUND(404),
    FORBIDDEN(403),
    INTERNAL_SERVER_ERROR(500);


    private int statusCode;

    HTTPStatusCodeType(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
