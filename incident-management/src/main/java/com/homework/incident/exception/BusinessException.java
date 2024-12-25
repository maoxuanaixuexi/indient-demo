package com.homework.incident.exception;

public class BusinessException extends RuntimeException{
    private int errorCode;  // 错误码
    private String errorMessage;  // 错误消息

    public BusinessException(int errorCode, String errorMessage) {
        super(errorMessage);  // 调用父类的构造函数
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
