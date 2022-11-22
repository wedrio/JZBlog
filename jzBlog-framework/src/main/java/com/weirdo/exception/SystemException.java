package com.weirdo.exception;

import com.weirdo.enums.AppHttpCodeEnum;

/**
 * @Author: xiaoli
 * @Date: 2022/11/20 --16:13
 * @Description:
 */
public class SystemException extends RuntimeException{
    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return message;
    }

    public SystemException(AppHttpCodeEnum httpCodeEnum) {
        super(httpCodeEnum.getMsg());
        this.code = httpCodeEnum.getCode();
        this.message = httpCodeEnum.getMsg();
    }
}
