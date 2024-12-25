package com.homework.incident.common;

public enum ResponseCode {

    UNLOGIN(401, "unLogin"),
    SUCCESS(200, "success"),
    FORBIDDEN(403, "forbidden"),
    FAILURE(500, "internal server error"),
    NOTEXIST(20001, "data is not exist");


    private int code;
    private String message;

    private ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ResponseCode(int code, String message, String key) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
