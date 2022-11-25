package com.weirdo.enums;

/**
 * @Author: xiaoli
 * @Date: 2022/11/5 --19:13
 * @Description:
 */
public enum AppHttpCodeEnum {
    SUCCESS(200,"操作成功"),
    NEED_LOGIN(401,"需要登录后操作"),
    NO_OPERATOR_AUTH(403,"没有权限操作"),
    SYSTEM_ERROR(500,"系统出现错误"),
    USERNAME_EXIST(501,"用户名已经存在"),
    NICKNAME_EXIST(501,"别名已经存在"),
    PHONE_NUMBER_EXIST(502,"手机号已经存在"),
    EMAIL_EXIST(503,"邮箱已经存在"),
    REQUIRE_USERNAME(504,"必须填写用户名"),
    REQUIRE_PASSWORD(504,"必须填写密码"),
    REQUIRE_NICKNAME(504,"必须填写别名"),
    REQUIRE_EMAIL(504,"必须填写邮箱"),
    LOGIN_ERROR(505,"用户名或密码错误"),
    COMMENT_NOT_NULL(506,"评论内容不能为空"),
    IMG_TYPE_ERROR(507,"图片格式错误，请上传.jpg或.png格式图片")
    ;
    Integer code;
    String msg;
    AppHttpCodeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
