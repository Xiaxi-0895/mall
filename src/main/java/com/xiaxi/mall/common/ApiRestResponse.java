package com.xiaxi.mall.common;

import com.xiaxi.mall.error.MallExceptionEnum;

public class ApiRestResponse<T> {
    private int code;
    private String msg;
    private T data;
    private static  final int SUCCESS_CODE = 200;
    private static final String SUCCESS_MSG = "success";

    @Override
    public String toString() {
        return "ApiRestResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public ApiRestResponse(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public ApiRestResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiRestResponse(){
        this(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> ApiRestResponse<T> success(T data) {
        ApiRestResponse<T> response = new ApiRestResponse<>();
        response.setData(data);
        return response;
    }

    public static <T> ApiRestResponse<T> success() {
        return new ApiRestResponse<T>(SUCCESS_CODE, SUCCESS_MSG);
    }

    public static <T> ApiRestResponse<T> error(int code, String msg) {
        return new ApiRestResponse<T>(code, msg);
    }

    public static <T> ApiRestResponse<T> error(MallExceptionEnum ex) {
        return new ApiRestResponse<T>(ex.getCode(), ex.getMessage());
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
