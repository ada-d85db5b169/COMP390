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
        result.code = 1;
        return result;
    }

    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 1;
        result.setData(data);
        return result;
    }

    public static <T> ApiResult<T> error (String message) {
        ApiResult<T> result = new ApiResult<>();
        result.code = 0;
        result.setMessage(message);
        return result;
    }


}
