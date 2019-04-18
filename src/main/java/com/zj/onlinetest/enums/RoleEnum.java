package com.zj.onlinetest.enums;

/**
 * @Auther: zj
 * @Date: 2019/4/17 14:29
 * @Description:
 */
public enum RoleEnum {

    ROLE_ADMIN(1,"ROLE_ADMIN"),
    ROLE_USER(2,"ROLE_USER")
    ;


    private Integer code;
    private String message;

    RoleEnum(Integer code, String message) {
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
