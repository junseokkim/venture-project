package com.venture.networking.global.common.response;

public record BaseResponse<T>(Status status, String errorMessage, T data) {

    public static BaseResponse<Void> ok() {
        return new BaseResponse<>(Status.SUCCESS, null, null);
    }

    public static <T> BaseResponse<T> ok(T data) {
        return new BaseResponse<>(Status.SUCCESS, null, data);
    }

    public static BaseResponse<Void> fail(String errorMessage) {
        return new BaseResponse<>(Status.FAIL, errorMessage, null);
    }

    public static <T> BaseResponse<T> fail(String errorMessage, T data) {
        return new BaseResponse<>(Status.FAIL, errorMessage, data);
    }

    public static BaseResponse<Void> error(String errorMessage) {
        return new BaseResponse<>(Status.ERROR, errorMessage, null);
    }

    public static <T> BaseResponse<T> error(String errorMessage, T data) {
        return new BaseResponse<>(Status.ERROR, errorMessage, data);
    }
}