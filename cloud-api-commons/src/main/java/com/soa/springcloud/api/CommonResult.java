package com.soa.springcloud.api;

import lombok.Data;

@Data
public class CommonResult<T> {
    //success / failure
    private String code;

    private String message;

    private T data;

    private CommonResult(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success(null, data);
    }

    public static <T> CommonResult<T> success(String message, T data) {
        return new CommonResult<>("success", message, data);
    }

    public static <T> CommonResult<T> failure() {
        return failure(null);
    }

    public static <T> CommonResult<T> failure(String message) {
        return failure(message, null);
    }

    public static <T> CommonResult<T> failure(String message, T data) {
        return new CommonResult<>("failure", message, data);
    }
}
