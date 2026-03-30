package com.btm.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

/**
 * @description 统一返回结果类
 * @author btm
 * @date 2026/03/30 10:18
 */
@Data
public class BaseResponse<T> implements Serializable {

    private static final HttpStatus OK = HttpStatus.OK;

    private static final HttpStatus SERVER_ERROR = HttpStatus.INTERNAL_SERVER_ERROR;

    private static final HttpStatus BAD_REQUEST = HttpStatus.BAD_REQUEST;

    private static final HttpStatus UNAUTHORIZED = HttpStatus.UNAUTHORIZED;

    private int code;

    private String msg;

    private T data;

    private BaseResponse(int code, String msg, T data) {
        this.setCode(code);
        this.setMsg(msg);
        this.setData(data);
    }

    public boolean isSuccess() {
        return this.code == OK.value();
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<T>(OK.value(), OK.getReasonPhrase(), null);
    }
    public static <T> BaseResponse<T> successMsg(String message) {
        return new BaseResponse<T>(OK.value(), message, null);
    }
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<T>(OK.value(), OK.getReasonPhrase(), data);
    }
    public static <T> BaseResponse<T> success(String message, T data) {
        return new BaseResponse<T>(OK.value(), message, data);
    }

    public static <T> BaseResponse<T> serverError() {
        return new BaseResponse<T>(SERVER_ERROR.value(), SERVER_ERROR.getReasonPhrase(), null);
    }
    public static <T> BaseResponse<T> serverError(String message) {
        return new BaseResponse<T>(SERVER_ERROR.value(), message, null);
    }
//    public static <T> BaseResponse<T> serverError(T data) {
//        return new BaseResponse<T>(SERVER_ERROR.value(), SERVER_ERROR.getReasonPhrase(), data);
//    }
    public static <T> BaseResponse<T> serverError(String message, T data) {
        return new BaseResponse<T>(SERVER_ERROR.value(), message, data);
    }

    public static <T> BaseResponse<T> badRequest() {
        return new BaseResponse<T>(BAD_REQUEST.value(), BAD_REQUEST.getReasonPhrase(), null);
    }
    public static <T> BaseResponse<T> badRequest(String message) {
        return new BaseResponse<T>(BAD_REQUEST.value(), message, null);
    }
    public static <T> BaseResponse<T> badRequest(String message, T data) {
        return new BaseResponse<T>(BAD_REQUEST.value(), message, data);
    }

    public static <T> BaseResponse<T> unAuth(String message) {
        return new BaseResponse<T>(UNAUTHORIZED.value(), message, null);
    }

    public static <T> BaseResponse<T> serverForbidden() {
        return new BaseResponse<T>(HttpStatus.FORBIDDEN.value(), "无访问权限！", null);
    }
    public static <T> BaseResponse<T> forbidden(String message) {
        return new BaseResponse<T>(HttpStatus.FORBIDDEN.value(), message, null);
    }

    public static <T> BaseResponse<T> serviceUnavailable(String message) {
        return new BaseResponse<T>(HttpStatus.SERVICE_UNAVAILABLE.value(), message, null);
    }
}
