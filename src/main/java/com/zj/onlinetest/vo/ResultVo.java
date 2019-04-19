package com.zj.onlinetest.vo;

/**
 * @Auther: zj
 * @Date: 2019/4/17 10:14
 * @Description: http请求返回的最外层对象
 */
public class ResultVo<T> {
    //状态码
    private Integer code;
    //提示信息
    private String msg;
    //具体内容
    private T data;

    Integer count;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
