package com.zj.onlinetest.enums;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:29
 * @Description:
 */
public enum  CommonEnum {

    FALSE(-1,"false"),
    USERNAMEORPASSWORD_ERROR(-2,"用户名或密码错误"),


    SUCCESS(1,"true"),
    LOGINSUCCEES(2,"管理员登录成功"),
    ;


    private Integer code;
    private String message;

    CommonEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
