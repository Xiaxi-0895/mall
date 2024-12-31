package com.xiaxi.mall.error;


public class MallException extends RuntimeException {
    private final int code;
    private final String msg;
    public MallException(final int code, final String msg) {
        this.code = code;
        this.msg = msg;
    }
    public MallException(MallExceptionEnum exceptionEnum) {
        this(exceptionEnum.getCode(), exceptionEnum.getMessage());
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
