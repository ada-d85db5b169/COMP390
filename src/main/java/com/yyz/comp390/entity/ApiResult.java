package com.yyz.comp390.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ApiResult<T> implements Serializable {

    private Integer code;
    private String message;
    private T data;

    public static <T> ApiResult<T> success() {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(200);
        return result;
    }

    public static <T> ApiResult<T> success(Integer code) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(200);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> success(Integer code, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> error (Integer code, String message) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static <T> ApiResult<T> error (Integer code, String message, T data) {
        ApiResult<T> result = new ApiResult<>();
        result.setCode(code);
        result.setMessage(message);
        result.setData(data);
        return result;
    }

}
