package com.homework.incident.common;

public class ResponseWrapper<T> {
    private int code;
    private String msg;
    private T data;

    private ResponseWrapper(ResponseWrapper.Builder<T> builder) {
        this.code = builder.code;
        this.msg = builder.message;
        this.data = builder.data;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public static class Builder<T> {
        private int code;
        private String message;
        private T data;

        public Builder() {
        }

        public ResponseWrapper<T> success() {
            this.code(ResponseCode.SUCCESS.getCode());
            this.message(ResponseCode.SUCCESS.getMessage());
            return this.build();
        }

        public ResponseWrapper<T> success(T data) {
            this.code(ResponseCode.SUCCESS.getCode());
            this.message(ResponseCode.SUCCESS.getMessage());
            this.data(data);
            return this.build();
        }

        public ResponseWrapper<T> failure(IResponseCode responseCode) {
            this.code(responseCode.getCode());
            this.message(responseCode.getMessage());
            return this.build();
        }

        public ResponseWrapper<T> failure(IResponseCode responseCode, T data) {
            this.code(responseCode.getCode());
            this.message(responseCode.getMessage());
            this.data(data);
            return this.build();
        }

        public ResponseWrapper.Builder<T> code(int code) {
            this.code = code;
            return this;
        }

        public ResponseWrapper.Builder<T> message(String message) {
            this.message = message;
            return this;
        }

        public ResponseWrapper.Builder<T> data(T data) {
            this.data = data;
            return this;
        }

        public ResponseWrapper<T> build() {
            return new ResponseWrapper(this);
        }
    }
}
